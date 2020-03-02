package asiantech.internship.summer.savedata

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import asiantech.internship.summer.Activity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.User
import kotlinx.android.synthetic.`at-hauha`.fragment_signup.*

class RegisterFragment : Fragment() {

    companion object {
        private const val IMAGE_GALLERY_CODE = 101
        private const val PERMISSION_CODE = 100
    }

    private var imageUri: Uri? = null
    private var user: User? = null
    private var db: AppDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "<font color=#329AE7>Already</font><font color=#ffffff> have an account? Sign-in</font>"
        tvSignUp.text = Html.fromHtml(text,1)
        db = AppDatabase.connectDatabase(requireContext())
        tvRegister.setOnClickListener {
            val name = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            var path = ""
            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Input username or password", Toast.LENGTH_SHORT).show()
            } else {
                if (imageUri != null) {
                    path = imageUri.toString()
                }
                user = User(username = name, password = password, path = path)
                user?.let { it1 -> db?.userDao()?.insertAll(it1) }
                edtEmail.text = Editable.Factory.getInstance().newEditable("")
                edtPassword.text = Editable.Factory.getInstance().newEditable("")
            }
            d("TAG11", "----" + db?.userDao()?.findUser(name, password))
        }
        imgAvatar.setOnClickListener {
            checkPermission()
        }
        tvSignUp.setOnClickListener {
            (activity as TodoActivity).replaceLoginFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_GALLERY_CODE) {
            imageUri = data?.data
            imgAvatar.setImageURI(imageUri)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_GALLERY_CODE)
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
    }

}
