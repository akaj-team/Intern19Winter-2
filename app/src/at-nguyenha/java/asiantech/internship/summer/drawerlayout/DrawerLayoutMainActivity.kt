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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.activity_drawerlayout.*
import kotlinx.android.synthetic.`at-nguyenha`.item_header.*
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.theartofdev.edmodo.cropper.CropImage

class DrawerLayoutMainActivity : AppCompatActivity() {

    private val items = mutableListOf<ItemModel>()
    private val adapterItem = RecyclerViewAdapter(items)
    private var imageUri: Uri? = null

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val IMAGE_CAPTURE_CODE = 1001
        private const val PERMISSION_CODE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawerlayout)

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
                val result = CropImage.getActivityResult(data)
                imgAvatar.setImageURI(result.uri)
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
        recyclerDrawer.adapter = adapterItem
        adapterItem.onItemClick = {
            showPictureDialog()
        }
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
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
                requestPermissions(permissions, PERMISSION_CODE)
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
                requestPermissions(permission, PERMISSION_CODE)
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
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
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
        adapterItem.let {
            items.add(ItemModel("nguyen.ha@asiantech.vn", R.drawable.ic_img_avatar, ""))
            items.add(ItemModel("", R.drawable.ic_speaker_notes_black_24dp, "Inbox"))
            items.add(ItemModel("", R.drawable.ic_box, "Outbox"))
            items.add(ItemModel("", R.drawable.ic_spam, "Spam"))
            items.add(ItemModel("", R.drawable.ic_trash, "Trash"))
        }
    }
}
