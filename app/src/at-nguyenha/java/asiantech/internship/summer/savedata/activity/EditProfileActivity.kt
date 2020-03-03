package asiantech.internship.summer.savedata.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.Utils
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.AccountModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-nguyenha`.fragment_register_savedata.*

class EditProfileActivity : AppCompatActivity() {

    private var getAction: String? = ""
    private var idToEdit: Int? = -1
    private var db: ConnectDataBase? = null
    private var account: AccountModel? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register_savedata)
        db = ConnectDataBase.getInMemoryDatabase(this)
        getDataBundle()
        setAvatar()
        initView()
        initListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Utils.IMAGE_PICK_CODE) {
            imageUri = data?.data
            Glide.with(layoutEditProfile).load(imageUri)
                    .placeholder(R.drawable.ic_account)
                    .into(imgAvatarRegister)
        }
    }

    private fun getDataBundle(){
        val bundle = intent.extras
        idToEdit = bundle?.getInt(Utils.PUT_ID)
        getAction = bundle?.getString(Utils.ACTION)
        account = idToEdit?.let { it1 -> db?.accountDao()?.getAccountById(it1) }
    }

    private fun initListener(){
        btnRegisterSaveData.setOnClickListener {
            editProfile()
            onBackPressed()
        }
    }

    private fun editProfile() {
        val username = edtUserNameRegister.text.toString()
        val nickname = edtNickNameRegister.text.toString()
        val password = edtPasswordRegister.text.toString()
        val avatar = imageUri.toString()
        idToEdit?.let { db?.accountDao()?.editAccount(it, username, nickname, password, avatar) }
    }

    private fun setAvatar() {
        imgAvatarRegister.setOnClickListener {
            requestPermission()
        }
    }

    private fun initView() {
        if (account != null) {
            edtUserNameRegister.setText(account?.userName)
            edtNickNameRegister.setText(account?.nickName)
            Glide.with(layoutEditProfile).load(Uri.parse(account?.avatarAccount))
                    .placeholder(R.drawable.ic_account)
                    .into(imgAvatarRegister)
        }
        if (getAction == Utils.ACTION_EDIT_PROFILE) {
            btnRegisterSaveData.text = getString(R.string.button_text_edit_profile)
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, Utils.GALLERY_PERMISSION_CODE)
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
        startActivityForResult(intent, Utils.IMAGE_PICK_CODE)
    }
}
