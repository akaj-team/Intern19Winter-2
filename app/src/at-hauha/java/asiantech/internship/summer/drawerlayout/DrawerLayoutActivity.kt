package asiantech.internship.summer.drawerlayout

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.Activity
import asiantech.internship.summer.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-hauha`.drawber_layout_activity.*
import kotlinx.android.synthetic.`at-hauha`.header_item.*



class DrawerLayoutActivity : AppCompatActivity() {
    private val menuItem = mutableListOf<Menu>()
    private lateinit var mAdapter: DrawerLayoutAdapter
    private var imageUri: Uri? = null

    companion object {
        private const val PERMISSION_CODE = 100
        private const val IMAGE_CAPTURE_CODE = 101
        private const val IMAGE_PICK_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawber_layout_activity)
        initData()
        initAdapter()

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            imageUri?.let { imageCropFunction(it) }
        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            imageUri?.let { imageCropFunction(it) }
        } else if (resultCode == Activity.RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            imgAvatar.setImageURI(result.uri)
        }
    }

    private fun imageCropFunction(imgUri: Uri) { // Image Crop Code
        CropImage.activity(imgUri)
                .start(this)
    }

    private fun initDrawable() {
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX: Float = drawerView.width * slideOffset
                flContainer.translationX = slideX
            }
        }
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
    }

    private fun initAdapter() {
        mAdapter = DrawerLayoutAdapter(menuItem)
        mAdapter.onItemClicked = {
            if (menuItem[it].name.isBlank()) {
                initAvatar()
            } else {
                menuItem[it].isStatus = true
                for (i in 1 until menuItem.size) {
                    if (i != it) menuItem[i].isStatus = false
                }
                mAdapter.notifyDataSetChanged()
            }

        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        initDrawable()
        recyclerView.adapter = mAdapter

    }

    private fun initAvatar() {
        val listDialog = arrayOf("Open Camera", "From Gallery")
        val dialogBuilder = AlertDialog.Builder(this)
        with(dialogBuilder) {
            setTitle("Choose the Action")
            setItems(listDialog) { _, which ->
                when (which) {
                    0 -> requestPermission()
                    1 -> pickImageFromGallery()
                }
            }
            show()
        }

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
                    == PERMISSION_DENIED ||
                    this.let {
                        ContextCompat.checkSelfPermission(
                                it,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                    == PERMISSION_DENIED) {
                val permission =
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val imgFromCamera = ContentValues()
        val resolver = this.contentResolver
        imgFromCamera.put(MediaStore.Images.Media.TITLE, "New Picture")
        imgFromCamera.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imgFromCamera)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun initData() {
        menuItem.apply {
            add(Menu(0, "", true))
            add(Menu(R.drawable.custom_inbox, "Inbox", true))
            add(Menu(R.drawable.custom_send, "Send", false))
            add(Menu(R.drawable.custom_spam, "Spam", false))
            add(Menu(R.drawable.custom_delete, "Trash", false))
        }
    }

}
