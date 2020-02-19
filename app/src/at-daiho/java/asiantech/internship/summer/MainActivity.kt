package asiantech.internship.summer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.helper.Utils


class MainActivity : AppCompatActivity() {

    companion object {
        private const val STORAGE_PERMISSION_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!checkStorePermission(STORAGE_PERMISSION_ID)) {
            showRequestPermission(STORAGE_PERMISSION_ID)
        }
    }

    private fun showRequestPermission(requestCode: Int) {
        Utils.requestPermission(this, requestCode, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun checkStorePermission(permission: Int): Boolean {
        return if (permission == STORAGE_PERMISSION_ID) {
            Utils.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_ID && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //TODO: Get songs here

        }
    }
}

