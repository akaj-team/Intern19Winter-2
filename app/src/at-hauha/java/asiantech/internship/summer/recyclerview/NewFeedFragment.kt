package asiantech.internship.summer.recyclerview

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.recyclerview.Utils.getPath
import kotlinx.android.synthetic.`at-hauha`.fragment_newfeed.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NewFeedFragment : Fragment() {

    companion object {
        private const val PERMISSION_CODE = 100
        private const val IMAGE_PICK_CODE = 101
    }

    private var imageUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_newfeed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        btnSave.setOnClickListener {
            uploadFile()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            imgBackGround.setImageURI(imageUri)
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun uploadFile() {
        val service = APIRetrofit.getRetrofitInstance()?.create(APIService::class.java)
        val file = File(getPath(requireContext(), imageUri))
        val fileRQ = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, fileRQ)
       // val name = RequestBody.create(MultipartBody.FORM,"hello")
        val call = service?.uploadData(body)
        call?.enqueue(object : Callback<RequestBody> {
            override fun onFailure(call: Call<RequestBody>, t: Throwable) {
                d("TAG11", t.message)
            }

            override fun onResponse(call: Call<RequestBody>, response: Response<RequestBody>) {
                d("TAG11", response.message())
            }
        })
    }

}

