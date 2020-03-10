package asiantech.internship.summer.recyclerview

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

object Utils {

    fun getPath(context: Context, uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = uri?.let { context.contentResolver.query(it, projection, null, null, null) }
                ?: return null
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(columnIndex)
        cursor.close()
        return s
    }
}