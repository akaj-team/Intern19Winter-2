package asiantech.internship.summer.drawerLayout

import android.Manifest
import android.app.Activity
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

import kotlinx.android.synthetic.`at-cuongle`.activity_drawer_layout.*
import kotlinx.android.synthetic.`at-cuongle`.row_top.*


class DrawerLayoutActivity : AppCompatActivity() {
    private val itemsMenu = mutableListOf<Menu>()
    private lateinit var menuAdapter: MenuAdapter
    private var imageAvatarUri: Uri? = null

    companion object {
        private const val PERMISSION_CODE = 100
        private const val IMAGE_CAPTURE_CODE = 101
        private const val IMAGE_GALLERY_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)
        initAdapter()
        initListeners()
        initData()
    }

    private fun initData() {
        itemsMenu.apply {
            add(Menu("", 1))
            add(Menu("Inbox", R.drawable.custom_icon_inbox))
            add(Menu("Outbox", R.drawable.custom_icon_send))
            add(Menu("Trash", R.drawable.custom_icon_delete))
            add(Menu("Spam", R.drawable.custom_icon_error))
        }
        menuAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        menuAdapter = MenuAdapter(itemsMenu)
        menuAdapter.onItemClicked = {
            Toast.makeText(this, "Selected " + itemsMenu[it].textTitle, Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = menuAdapter
    }

    private fun initListeners() {
        menuAdapter.onAvatarClick = {
            requestPermission()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {

            imgAvatar.setImageURI(imageAvatarUri)

        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_GALLERY_CODE) {
            imageAvatarUri = data!!.data
            imgAvatar.setImageURI(imageAvatarUri)
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
}
