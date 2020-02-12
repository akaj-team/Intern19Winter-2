package asiantech.internship.summer.layoutandroid

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_edit_profile.*


class EditProfileFragment : Fragment() {
    lateinit var bitMap: Bitmap

    companion object {
        const val CAMERA_REQUEST_CODE = 101
        const val WRITE_CODE = 102
        const val FULLNAME: String = "fullname"
        const val EMAIL: String = "email"
        const val BIO: String = ""
        val IMG: Bitmap? = null
        fun profileEdit(name: String, email: String, bio: String, img: CircleImageView): EditProfileFragment {
            val editProfileFragment = EditProfileFragment()
            val bundle = Bundle()
            bundle.putString(FULLNAME, name)
            bundle.putString(BIO, bio)
            bundle.putString(EMAIL, email)
            bundle.putString(IMG.toString(), img.toString())
            editProfileFragment.arguments = bundle
            return editProfileFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        edtFullName.setText(arguments?.getString(FULLNAME))
        edtViewEmail2.setText(arguments?.getString(EMAIL))
        edtBio.setText(arguments?.getString(BIO))
        super.onViewCreated(view, savedInstanceState)
        imgAvatar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE)
            }
        })
        tvBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                this@EditProfileFragment.fragmentManager?.popBackStack()
            }
        })
        btnProfile.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val userProfileFragment: UserProfileFragment = UserProfileFragment()
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayout, userProfileFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                this@EditProfileFragment.fragmentManager?.popBackStack()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
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
        //changeAvatar = UserProfileFragment()
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            bitMap = data.extras?.get("data") as Bitmap // data luon la mac dinh
            imgAvatar.setImageBitmap(bitMap)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
