package asiantech.internship.summer.drawerview

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-uyennguyen`.activity_drawerlayout.*
import kotlinx.android.synthetic.`at-uyennguyen`.row_item_header.*


class DrawerLayoutActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_REQUEST_CODE = 101
        const val LOAD_IMAGE_GALLERY = 202
    }

    private lateinit var imageGallery: Uri
    private lateinit var imageCamera: Uri
    private val itemModel = mutableListOf<ItemModel?>()
    private val itemAdapter = ItemAdapter(itemModel, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawerlayout)
        initData()
        drawerSlide()
        recyclerViewDrawer.adapter = itemAdapter
        itemAdapter.onItemClicked = {
            setAvatarDialog()
        }
    }

    private fun setAvatarDialog() {
        val avatarDialog = AlertDialog.Builder(this)
        avatarDialog.setTitle("Choose Avatar by : ")
        avatarDialog.setItems(arrayOf("Gallery", "Camera")) { _, which ->
            when (which) {
                0 -> setAvatarByGallery()
                1 -> setAvatarByCamera()
            }
        }
        avatarDialog.show()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun setAvatarByGallery() {
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), LOAD_IMAGE_GALLERY)
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun setAvatarByCamera() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE)
    }


    @SuppressLint("ShowToast")
    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val imgFromCamera = ContentValues()
            val resolver = this.contentResolver
            imageCamera = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imgFromCamera)!!
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageCamera)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }
        if (requestCode == LOAD_IMAGE_GALLERY && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val gallery = Intent(Intent.ACTION_GET_CONTENT)
            gallery.type = "image/*"
            startActivityForResult(gallery, LOAD_IMAGE_GALLERY)

        } else {
            if (!this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                this.apply {
                    Toast.makeText(this@DrawerLayoutActivity, "Please open camera permission on settings", Toast.LENGTH_LONG)
                }
            }
            if (!this.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                this.apply {
                    Toast.makeText(this@DrawerLayoutActivity, "Please open camera permission on settings", Toast.LENGTH_LONG)
                }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            cropImage(imageCamera)
        } else if (requestCode == LOAD_IMAGE_GALLERY && resultCode == RESULT_OK) run {
            imageGallery = data?.data!!
            cropImage(imageGallery)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            imgAvatar.setImageURI(result.uri)
        }
    }

    private fun cropImage(imageUri: Uri) {
        CropImage.activity(imageUri)
                .start(this)
    }

    private fun drawerSlide() {
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slide = drawerView.width * slideOffset
                frameLayout.translationX = slide
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun initData() {
        itemModel.add(ItemModel(R.drawable.setting, R.drawable.settingcolor, "Cài đặt", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.bell, R.drawable.bellcolor, "Thông báo", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.calendar, R.drawable.calendarcolor, "Lịch trình", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.signin, R.drawable.signincolor, "Tài khoảng", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.protect, R.drawable.protectcolor, "Quyền lợi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.spam, R.drawable.spamcolor, "Lỗi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.trash, R.drawable.trashcolor, "Thùng rác", "uyen.nguyen@gmail.com"))
        itemModel.shuffle()
    }

}




