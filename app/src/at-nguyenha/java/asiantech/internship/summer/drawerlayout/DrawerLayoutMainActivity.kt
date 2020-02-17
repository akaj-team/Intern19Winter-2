package asiantech.internship.summer.drawerlayout

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-nguyenha`.activity_drawer_layout.*
import kotlinx.android.synthetic.`at-nguyenha`.item_drawer_header.*

class DrawerLayoutMainActivity : AppCompatActivity() {
    private val items = mutableListOf<ItemModel>()
    private val adapterDrawer = DrawerAdapter(items)
    private var imageUri: Uri? = null

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val IMAGE_CAPTURE_CODE = 1001
        private const val CAMERA_PERMISSION_CODE = 2000
        private const val GALLERY_PERMISSION_CODE = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)
        initView()
        initAdapter()
        initData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            imageCropFunction(imageUri)

        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            imageCropFunction(imageUri)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                val resultImage = CropImage.getActivityResult(data)
                imgAvatar.setImageURI(resultImage.uri)
            }
        }
    }

    private fun initView() {
        recyclerDrawer.layoutManager = LinearLayoutManager(this)
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerMainActivity,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX: Float = drawerView.width * slideOffset
                layoutContent.translationX = slideX
            }
        }
        drawerMainActivity.addDrawerListener(actionBarDrawerToggle)
    }

    private fun initAdapter() {
        recyclerDrawer.adapter = adapterDrawer
        adapterDrawer.onItemClick = {
            displaySelectPhotoDialog()
        }
    }

    private fun displaySelectPhotoDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle(getString(R.string.dialog_title))
        val pictureDialogItems = arrayOf(getString(R.string.dialog_title_gallery), getString(R.string.dialog_title_camera))
        pictureDialog.setItems(pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> onClickOpenGallery()
                1 -> onClickOpenCamera()
            }
        }
        pictureDialog.show()
    }

    private fun onClickOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
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

    private fun onClickOpenCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, CAMERA_PERMISSION_CODE)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, getString(R.string.media_title))
        values.put(MediaStore.Images.Media.DESCRIPTION, getString(R.string.media_description))
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    private fun imageCropFunction(imageUri: Uri?) {
        CropImage.activity(imageUri)
                .start(this)
    }

    private fun initData() {
        adapterDrawer.let {
            items.add(ItemModel("nguyen.ha@asiantech.vn", R.drawable.ic_img_avatar, ""))
            items.add(ItemModel("", R.drawable.selector_img_outbox, "Inbox"))
            items.add(ItemModel("", R.drawable.selector_img_inbox, "Outbox"))
            items.add(ItemModel("", R.drawable.selector_img_spam, "Spam"))
            items.add(ItemModel("", R.drawable.selector_img_trash, "Trash"))
        }
    }
}
