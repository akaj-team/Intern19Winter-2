package asiantech.internship.summer.layout

import android.Manifest
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.`at-hauha`.fragment_profile.*
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager

import asiantech.internship.summer.R

import android.net.Uri
import android.os.Build


import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.`at-hauha`.fragment_profile.imgAvatar


class FragmentProfile : Fragment() {

    var image_uri: Uri? = null
    private var mName = ""
    private var mEmail = ""
    private var mAvatar = ""

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        private const val ARG_AVATAR = "avatar"
        private val PERMISSION_CODE = 1000
        private val IMAGE_CAPTURE_CODE = 1001

        fun newInstance(mName: String, mEmail: String, mAvatar: String) = FragmentProfile().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, mName)
                putString(ARG_EMAIL, mEmail)
                putString(ARG_AVATAR, mAvatar)

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            mName = it.get(ARG_NAME).toString()
            mEmail = it.get(ARG_EMAIL).toString()
            mAvatar = it.get(ARG_AVATAR).toString()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtFullName.setText(mName)
        //edtEmail.setText(mEmail)
        if("".equals(mAvatar)){
            imgAvatar.setImageURI(Uri.parse(mAvatar))
        }
        txtEditProfilePicture.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    //permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    openCamera()
                }
            } else {
                //system os is < marshmallow
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        val resolver = context?.contentResolver
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        image_uri = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this.activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == android.app.Activity.RESULT_OK) {
            //set image captured to image view
            imgAvatar.setImageURI(image_uri)
            mAvatar = image_uri.toString()
            txtBack.setOnClickListener {
                mName = edtFullName.text.toString()
                fragmentManager?.beginTransaction()
                        ?.replace(R.id.flContainer, FragmentUserProfile.newInstance(mName, mEmail, mAvatar), null)
                        ?.addToBackStack(null)
                        ?.commit()
            }
        }
    }
//    interface UpdateData{
//        fun updateData(image_uri:Uri){
//        }
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//       var callBack = context as? UpdateData
//        if (callBack == null) {
//            throw ClassCastException("$context must implement OnArticleSelectedListener")
//        }
//    }
}