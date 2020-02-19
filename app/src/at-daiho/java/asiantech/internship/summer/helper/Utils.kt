package asiantech.internship.summer.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class Utils {

    companion object {
        fun checkPermission(context: Context, permission: String?): Boolean {
            return (ActivityCompat.checkSelfPermission(context, permission!!) == PackageManager.PERMISSION_GRANTED)
        }

        fun requestPermission(activity: Activity, requestCode: Int, vararg permissions: String?) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
    }
}
