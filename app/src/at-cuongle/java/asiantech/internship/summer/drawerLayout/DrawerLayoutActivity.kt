package asiantech.internship.summer.drawerLayout

import android.Manifest
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import asiantech.internship.summer.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-cuongle`.activity_drawer_layout.*
import kotlinx.android.synthetic.`at-cuongle`.item_drawer_header.*

class DrawerLayoutActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_CODE = 100
        private const val IMAGE_CAPTURE_CODE = 101
        private const val IMAGE_GALLERY_CODE = 110
    }

    private val itemsMenu = mutableListOf<DrawerItem?>()
    private lateinit var menuAdapter: DrawerAdapter
    private var imageAvatarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)
        initAdapter()
        initListeners()
        initData()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialog()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_CAPTURE_CODE -> cropImage(imageAvatarUri)
            IMAGE_GALLERY_CODE -> {
                imageAvatarUri = data?.data
                cropImage(imageAvatarUri)
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                imgAvatar.setImageURI(result.uri)
            }
        }
    }

    private fun initAdapter() {
        menuAdapter = DrawerAdapter(itemsMenu)
        menuAdapter.onItemClicked = {
            Toast.makeText(this, "Selected " + itemsMenu[it]?.textTitle, Toast.LENGTH_SHORT).show()
        }
        menuAdapter.onAvatarClick = {
            requestPermission()
        }
        recyclerView.adapter = menuAdapter
    }

    private fun initListeners() {
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.tv_drawer_open,
                R.string.tv_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                frContent.translationX = drawerView.width * slideOffset
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun initData() {
        itemsMenu.add(null)
        itemsMenu.apply {
            add(DrawerItem("Inbox", R.drawable.selector_img_inbox))
            add(DrawerItem("Outbox", R.drawable.selector_img_send))
            add(DrawerItem("Trash", R.drawable.selector_img_delete))
            add(DrawerItem("Spam", R.drawable.selector_img_error))
        }
        menuAdapter.notifyDataSetChanged()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
                    == PackageManager.PERMISSION_DENIED ||
                    this.let {
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

    private fun showDialog() {
        val dialogOption = this.let { AlertDialog.Builder(it) }
        dialogOption.apply {
            setTitle("Select Action")
            val dialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
            setItems(dialogItems
            ) { _, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            show()
        }
    }

    private fun openCamera() {
        val imgFromCamera = ContentValues()
        val resolver = this.contentResolver
        imageAvatarUri =
                resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imgFromCamera)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageAvatarUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_GALLERY_CODE)
    }

    private fun cropImage(imageUri: Uri?) {
        CropImage.activity(imageUri)
                .start(this)
    }
}
