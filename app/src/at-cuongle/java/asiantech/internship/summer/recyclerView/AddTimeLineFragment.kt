package asiantech.internship.summer.recyclerView

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.Activity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_add_time_line.*

class AddTimeLineFragment : Fragment() {
    companion object {
        private const val PERMISSION_CODE = 100
        private const val IMAGE_CAPTURE_CODE = 101
    }

    private var uriFood: Uri? = null
    private var timeLineItem: TimeLineItem? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_time_line, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(context, getString(R.string.toast_permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            uriFood?.let {
                imgFood.setImageURI(it)
                Log.i("XXX", getRealPathFromURI(requireContext(),it).toString())
            }
        }
    }

    private fun initListeners() {

        imgFood.setOnClickListener {
            requestPermission()
        }

        btnSave.setOnClickListener {
            timeLineItem = TimeLineItem()
            (activity as? TimelineActivity)?.replaceFragment(TimeLineFragment.newInstance(uriFood?.let { it1 -> getRealPathFromURI(requireContext(), it1) }))
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
                    == PackageManager.PERMISSION_DENIED ||
                    context?.let {
                        ContextCompat.checkSelfPermission(
                                it, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                    == PackageManager.PERMISSION_DENIED) {
                val permission =
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val imgFromCamera = ContentValues()
        val resolver = context?.contentResolver
        imgFromCamera.put(MediaStore.Images.Media.TITLE, "New Picture")
        imgFromCamera.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        uriFood = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imgFromCamera)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFood)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    @SuppressLint("Recycle")
    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(uri, proj, null, null, null)
            Log.i("XXX",context.contentResolver.getType(uri).toString())
            val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            columnIndex?.let { cursor?.getString(it) }
        } catch (e: Exception){
            Log.e("XXX", "getRealPathFromURI Exception : $e")
            ""
        } finally {
            cursor?.close()
        }
    }
}
