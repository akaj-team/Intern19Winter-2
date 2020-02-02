package asiantech.internship.summer.layoutandroid

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_edit_profile.*


class EditProfileFragment : Fragment(){
    lateinit var changeAvatar: ChangeAvatar
    lateinit var bitMap: Bitmap

    companion object {
        const val CAMERA_REQUEST_CODE = 101
        const val WRITE_CODE = 102
        val FULLNAME:String="fullname"
        val EMAIL:String="email"
        fun profileEdit(name:String,email:String): EditProfileFragment {
            val editProfileFragment=EditProfileFragment()
            val bundle=Bundle()
            bundle.putString(FULLNAME,name)
            bundle.putString(EMAIL,email)
            editProfileFragment.arguments=bundle
            return editProfileFragment
        }
    }

    //private lateinit var output: File

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editFullName.setText(arguments?.getString(FULLNAME))
        editViewEmail2.setText(arguments?.getString(EMAIL))
        youravatar.setImageBitmap(bitMap)
        super.onViewCreated(view, savedInstanceState)
        youravatar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE) // tại sao chỉ có code camera mà ko có code write
                //requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_CODE)
                //hỏi quyền
            }
        })
        textBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                this@EditProfileFragment.fragmentManager?.popBackStack()
            }
        })
        btnProfile.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("change", changeAvatar.toString())
                changeAvatar.profile(editFullName.text.toString(),editBio.text.toString())
                if (bitMap != null) {
                    changeAvatar.imageUser(bitMap)
                }
                this@EditProfileFragment.fragmentManager?.popBackStack()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
            // mở camera
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
                youravatar.setImageBitmap(bitMap)
//                val fileOutputStream = FileOutputStream(Environment.getExternalStorageDirectory().absolutePath+"/savepicture.png")
//                bitMap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream)
            }
            super.onActivityResult(requestCode, resultCode, data)

    }
}
