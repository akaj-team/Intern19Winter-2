package asiantech.internship.summer.apptodolist

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.Activity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_edit_profile.*

class EditProfileFragment : Fragment() {

    companion object {
        private const val GALLERY_CODE = 101
        private const val USER = "user"
        fun newInstance(user: User): EditProfileFragment {
            val editProfileFragment = EditProfileFragment()
            val bundle = Bundle()
            bundle.putParcelable(USER, user)
            editProfileFragment.arguments = bundle
            return editProfileFragment
        }
    }

    private var databaseManager: DatabaseManager? = null
    private lateinit var imageGallery: Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        context?.apply { databaseManager = DatabaseManager(this) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getParcelable<User>(USER) as User
        imageGallery = Uri.parse(user.avatar)
        val idUser = user.idUser
        imgAvatarProfile.setImageURI(Uri.parse(user.avatar))
        edtUsernameEdit.setText(user.nameUser)
        edtNicknameEdit.setText(user.nickName)
        edtPasswordEdit.setText(user.passWord)
        btnEdit.isClickable = false
        btnEdit.setOnClickListener {
            val userName: String = edtUsernameEdit.text.toString()
            val userNickname: String = edtNicknameEdit.text.toString()
            val userPassword: String = edtPasswordEdit.text.toString()
            val users = User(idUser = idUser, nameUser = userName, nickName = userNickname, passWord = userPassword, avatar = imageGallery.toString())
            databaseManager?.updateUser(users)
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutTodo, HomeToDoFragment(users))
                    ?.addToBackStack(null)
                    ?.commit()
        }
        imgAvatarProfile.setOnClickListener {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_CODE)
        } else {
            if (!this.shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(context, "Please open gallery permission on settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            imageGallery = data?.data!!
            Log.d("images", "Select from image gallery: " + imageGallery.toString())
            imgAvatarProfile.setImageURI(imageGallery)
        }
    }
}
