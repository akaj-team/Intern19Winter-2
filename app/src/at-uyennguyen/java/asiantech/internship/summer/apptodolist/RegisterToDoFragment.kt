package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_register_todo.*

class RegisterToDoFragment : Fragment() {

    companion object {
        private const val ID_DEFAULT = -1
        private const val SHARED_ID = "id"
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
        private const val GALLERY_CODE = 101
    }

    private var imageGallery: Uri? = null
    private var sharedPreferences: SharedPreferences? = null
    private var databaseManager: DatabaseManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_todo, container, false)
        context?.apply { databaseManager = DatabaseManager(this) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        val idDefault = sharedPreferences?.getInt(SHARED_ID, ID_DEFAULT)
        Log.d("frag", idDefault.toString())
        if (idDefault != ID_DEFAULT) {
            val user = idDefault?.let { databaseManager?.getUserById(it) } //val user = databaseManager.getUserById(idDefault)
            if (user != null) {
                if (user.size > 0 && user[0].idUser != -1) {
                    val homeToDoFragment = HomeToDoFragment(user[0])
                    activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frameLayoutTodo, homeToDoFragment)
                            ?.commit()
                }
            }
        } else {
            btRegister.setOnClickListener {
                if (edtUsernameRegister.text.toString().isEmpty() || edtNicknameRegister.text.toString().isEmpty() || edtPasswordRegister.text.toString().isEmpty() || imageGallery == null) {
                    Toast.makeText(this.context, "Please enter full information", Toast.LENGTH_SHORT).show()
                } else {
                    val name: String = edtUsernameRegister.text.toString()
                    val nickname: String = edtNicknameRegister.text.toString()
                    val password: String = edtPasswordRegister.text.toString()
                    Log.d("images", "Save to database:" + imageGallery.toString())
                    val user = User(idUser = 0, nameUser = name, nickName = nickname, passWord = password, avatar = imageGallery.toString())
                    databaseManager?.addUser(user)
                    val homeToDoFragment = HomeToDoFragment(user)
                    activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frameLayoutTodo, homeToDoFragment)
                            ?.commit()
                }
            }
        }
        tvLoginhere.setOnClickListener {
            val loginToDoFragment = LoginToDoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutTodo, loginToDoFragment)
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
            Log.d("images", "Select From Image Gallery: " + imageGallery.toString())
            imgAvatarProfile.setImageURI(imageGallery)
        }
    }
}
