package asiantech.internship.summer.layout


import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_edit_profile.*


/**
 * A simple [Fragment] subclass.
 */
class EditProfileFragment : Fragment() {
    private var mName: String = ""
    private var mEmail: String = ""
    private var mAvatar: String = ""
    private var imgBackGround: Uri? = null

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        private const val PERMISSION_CODE = 100
        private const val IMAGE_CAPTURE_CODE = 101

        fun newInstance(mName: String, mEmail: String) = EditProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, mName)
                putString(ARG_EMAIL, mEmail)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            mName = it?.getString(ARG_NAME).toString()
            mEmail = it?.getString(ARG_EMAIL).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtFullName.setText(mName)
        edtEmail.setText(mEmail)
        imgAvatar.setOnClickListener {
            requestPermission()
        }
        btnSave.setOnClickListener {
            mName = edtFullName.text.toString()
            mEmail = edtEmail.text.toString()
            (activity as? LayoutMainActivity)?.replaceFragment(LoginFragment.newInstance(mName, mEmail))
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
                    == PackageManager.PERMISSION_DENIED ||
                    context?.let {
                        ContextCompat.checkSelfPermission(
                                it,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imgAvatar.setImageURI(imgBackGround)
            mAvatar = imgBackGround.toString()
            tvBack.setOnClickListener {
                (activity as? LayoutMainActivity)?.replaceFragment(UserProfileFragment.newInstance(mName, mEmail, mAvatar))
            }
        }
    }

    private fun openCamera() {
        val imgFromCamera = ContentValues()
        val resolver = context?.contentResolver
        imgFromCamera.put(MediaStore.Images.Media.TITLE, "New Picture")
        imgFromCamera.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imgBackGround =
                resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imgFromCamera)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgBackGround)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }
}
