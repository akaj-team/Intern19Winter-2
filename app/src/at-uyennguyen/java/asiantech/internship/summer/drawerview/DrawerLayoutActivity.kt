package asiantech.internship.summer.drawerview

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_drawerlayout.*
import kotlinx.android.synthetic.`at-uyennguyen`.row_item_header.*

class DrawerLayoutActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_REQUEST_CODE = 101
        const val LOAD_IMAGE_GALLERY = 202
        const val SAMPLE_CROPPED_IMG_NAME = "SampleCropImg"
        const val CROP_CODE = 1
    }

    private lateinit var image: Uri
    private lateinit var bitMap: Bitmap
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
        requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    @SuppressLint("ShowToast")
    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
        if (requestCode == LOAD_IMAGE_GALLERY && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val gallery = Intent(Intent.ACTION_GET_CONTENT)
            gallery.setType("image/*")
            startActivityForResult(gallery, LOAD_IMAGE_GALLERY)

        } else {
            if (!this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                this.apply {
                    Toast.makeText(this@DrawerLayoutActivity, "Please open camera permission on settings", Toast.LENGTH_LONG)
                    Log.d("aaa", "uyen")
                }
            }
            if (!this.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                this.apply {
                    Toast.makeText(this@DrawerLayoutActivity, "Please open camera permission on settings", Toast.LENGTH_LONG)
                    Log.d("aaa", "uyen")
                }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun drawerSlide() {
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                var slide = drawerView.width * slideOffset
                frameLayout.translationX = slide
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            bitMap = data?.extras?.get("data") as Bitmap
            imgAvatar.setImageBitmap(bitMap)
//            cropImage()
        }
//        else if (requestCode == CROP_CODE) {
//            val extras = data?.getExtras()
//            val thePic = extras?.getParcelable<Bitmap>("data")
//            imgAvatar.setImageBitmap(thePic)
//        }
        if (requestCode == LOAD_IMAGE_GALLERY && resultCode == RESULT_OK) run {
            //            cropImage(image)
            image = data?.data!!
            imgAvatar.setImageURI(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun cropImage(image: Uri) {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(image, "image/*")
            cropIntent.putExtra("crop", "true")
            cropIntent.putExtra("aspectX", 2)
            cropIntent.putExtra("aspectY", 1)
            cropIntent.putExtra("outputX", 256)
            cropIntent.putExtra("outputY", 256)
            cropIntent.putExtra("return-data", true)
            startActivityForResult(cropIntent, CROP_CODE)
        } catch (anfe: ActivityNotFoundException) {
            val toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun initData() {
        itemModel.add(ItemModel(R.drawable.setting, R.drawable.settingcolor, "Cài đặt", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.bell, R.drawable.bellcolor, "Thông báo", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.calendar, R.drawable.calendarcolor, "Lịch trình", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.signin, R.drawable.signincolor, "Tài khoảng", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.protect, R.drawable.protectcolor, "Quyền lợi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.spam, R.drawable.spamcolor, "Lỗi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.trash, R.drawable.trashcolor, "Thùng rác", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.protect, R.drawable.protectcolor, "Quyền lợi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.spam, R.drawable.spamcolor, "Lỗi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.setting, R.drawable.settingcolor, "Cài đặt", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.bell, R.drawable.bellcolor, "Thông báo", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.calendar, R.drawable.calendarcolor, "Lịch trình", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.signin, R.drawable.signincolor, "Tài khoảng", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.protect, R.drawable.protectcolor, "Quyền lợi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.spam, R.drawable.spamcolor, "Lỗi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.trash, R.drawable.trashcolor, "Thùng rác", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.protect, R.drawable.protectcolor, "Quyền lợi", "uyen.nguyen@gmail.com"))
        itemModel.add(ItemModel(R.drawable.spam, R.drawable.spamcolor, "Lỗi", "uyen.nguyen@gmail.com"))
        itemModel.shuffle()
    }

}

