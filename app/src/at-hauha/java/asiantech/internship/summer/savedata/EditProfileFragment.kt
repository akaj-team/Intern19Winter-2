package asiantech.internship.summer.savedata

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.Activity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.User
import kotlinx.android.synthetic.`at-hauha`.fragment_edit_profile.*

@Suppress("NAME_SHADOWING")
class EditProfileFragment : Fragment() {

    companion object {
        private const val IMAGE_GALLERY_CODE = 101
        private const val PERMISSION_CODE = 100
        private const val ARG_USER = "user"
        fun newInstance(user: User) = EditProfileFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_USER, user)
            }
        }
    }

    private var imageUri: Uri? = null
    private var db: AppDatabase? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            user = getSerializable(ARG_USER) as User?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtEmail.text = Editable.Factory.getInstance().newEditable(user?.username)
        edtPassword.text = Editable.Factory.getInstance().newEditable(user?.password)
        if(user?.path?.run { isEmpty() }!!){
            imgAvatar.setImageResource(R.drawable.ic_empty_avatar)
        }else{
            imgAvatar.setImageURI(Uri.parse(user?.path))
        }

        imgAvatar.setOnClickListener {
            checkPermission()
        }
        db = AppDatabase.connectDatabase(requireContext())
        imgSave.setOnClickListener {
            val name = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            var path = user?.path
            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Input username or password", Toast.LENGTH_SHORT).show()
            } else {
                if (imageUri != null) {
                    path = imageUri.toString()
                }
                user?.id?.let { it1 -> path?.let { it2 -> db?.userDao()?.updateData(name, password, it2, it1) } }
            }
            Log.d("TAG11", "----" + db?.userDao()?.findUser(name, password))
            user  = db?.userDao()?.findUser(name,password)
        }
        imgExit.setOnClickListener {
            user?.let { it -> (activity as? TodoActivity)?.replaceMenuFragment(it)}
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
}