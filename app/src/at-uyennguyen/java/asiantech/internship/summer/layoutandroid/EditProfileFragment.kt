package asiantech.internship.summer.layoutandroid

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_edit_profile.*


class EditProfileFragment : Fragment() {
    private var imageCamera: Uri? = null

    companion object {
        const val CAMERA_REQUEST_CODE = 101
        const val FULLNAME: String = "fullname"
        const val EMAIL: String = "email"
        const val BIO: String = "bio"
        const val AVATAR: String = "img"
        fun profileEdit(name: String, email: String, bio: String, img: String?): EditProfileFragment {
            val editProfileFragment = EditProfileFragment()
            val bundle = Bundle()
            bundle.putString(FULLNAME, name)
            bundle.putString(BIO, bio)
            bundle.putString(EMAIL, email)
            bundle.putString(AVATAR, img)
            editProfileFragment.arguments = bundle
            return editProfileFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getString(AVATAR) == "") {
            imgAvatar.setImageResource(R.drawable.img_avatarman)
        } else {
            imgAvatar.setImageURI(Uri.parse(arguments?.getString(AVATAR)))
            imageCamera = Uri.parse(arguments?.getString(AVATAR))
        }
        edtFullName.setText(arguments?.getString(FULLNAME))
        edtEmail2.setText(arguments?.getString(EMAIL))
        edtBio.setText(arguments?.getString(BIO))
        imgAvatar.setOnClickListener { requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE) }
        tvBack.setOnClickListener { this@EditProfileFragment.fragmentManager?.popBackStack() }
        btnProfile.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, UserProfileFragment.newProfile(edtFullName.text.toString(), edtEmail2.text.toString(), imageCamera.toString(), edtBio.text.toString()))
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val imgFromCamera = ContentValues()
            val resolver = context?.contentResolver
            imageCamera = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imgFromCamera)!!
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCamera)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            if (!this@EditProfileFragment.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                view?.apply {
                    val snackbar =
                            Snackbar.make(this, R.string.message_no_storage_permission_snackbar, Snackbar.LENGTH_LONG)
                    snackbar.setAction(R.string.settings, object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            this@EditProfileFragment.startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                        }
                    })
                    snackbar.show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            imgAvatar.setImageURI(imageCamera)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
