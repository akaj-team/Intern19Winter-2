package asiantech.internship.summer.savedata.fragment

import android.Manifest
import android.app.Activity
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
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.AccountModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_register_savedata.*

class RegisterFragment : Fragment() {

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val GALLERY_PERMISSION_CODE = 2001
    }

    private var db: ConnectDataBase? = null
    private var imageUri: Uri? = null
    private var account: AccountModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_savedata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = ConnectDataBase.getInMemoryDatabase(requireContext())
        btnRegisterSaveData.setOnClickListener {
            account = AccountModel(
                    userName = edtUserNameRegister.text.toString(),
                    nickName = edtNickNameRegister.text.toString(),
                    password = edtPasswordRegister.text.toString(),
                    avatarAccount = imageUri.toString())

            account?.let { it1 -> db?.accountDao()?.insertAccount(it1) }
            edtUserNameRegister.setText("")
            edtNickNameRegister.setText("")
            edtPasswordRegister.setText("")
            Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_SHORT).show()
        }
        imgAvatarRegister.setOnClickListener {
            requestPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            imgAvatarRegister.setImageURI(imageUri)
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, GALLERY_PERMISSION_CODE)
            } else {
                openGallery()
            }
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
}
