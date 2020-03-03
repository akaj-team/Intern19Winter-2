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
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.DataConnection
import asiantech.internship.summer.layout.database.model.User
import kotlinx.android.synthetic.`at-cuongle`.fragment_edit_profile.*

class EditProfileFragment : Fragment() {
    companion object {
        private const val ARG_USER = "user"
        private const val PERMISSION_CODE = 100
        private const val IMAGE_CAPTURE_CODE = 101

        fun newInstance(user: User) = EditProfileFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_USER, user)
            }
        }
    }

    private var userEmail: String = ""
    private var user: User? = null
    private var imgBackGround: Uri? = null
    private var db: DataConnection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            user = it?.getSerializable(ARG_USER) as User
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataConnection.connectData(requireContext())
        edtEmail.setText(user?.userName)
        if (user?.path != "") {
            imgAvatar.setImageURI(Uri.parse(user?.path))
        }
        imgAvatar.setOnClickListener {
            requestPermission()
        }
        btnSave.setOnClickListener {
            userEmail = edtEmail.text.toString()
            user?.let { user?.uid?.let { it1 -> db?.userDao()?.updateData(it1, userEmail, user?.path) } }
            user?.let { (activity as? LayoutMainActivity)?.replaceFragment(MainScreenFragment.newInstance(it)) }
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
                showDialog()
            }
        } else {
            showDialog()
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
            user?.path = imgBackGround.toString()
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

    private fun showDialog() {
        val editText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
        dialogOption.setView(editText)
        dialogOption.apply {
            setTitle("Change the Avatar")
            setPositiveButton("Open Camera") { _, _ ->
                openCamera()
            }
            setNegativeButton("Open Gallery") { _, _ ->
                (activity as? LayoutMainActivity)?.replaceFragment(GalleryFragment())
            }
            show()
        }
    }

}
