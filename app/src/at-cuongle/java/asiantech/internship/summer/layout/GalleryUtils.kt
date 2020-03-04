@file:Suppress("DEPRECATION")

package asiantech.internship.summer.layout

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore

object GalleryUtils {
    private var imagePath = mutableListOf<Gallery>()
    private var imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private var columnIndexData = 0
    private var columnIndexFolderName: Int = 0
    private var absolutePathOfImage: String? = null
    @SuppressLint("InlinedApi")
    private var projection = arrayOf(MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

    @SuppressLint("Recycle", "InlinedApi")
    internal fun getImage(context: Context): MutableList<Gallery> {
        val cursor = context.contentResolver.query(imageUri, projection, null, null, null)
        columnIndexData = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)!!
        columnIndexFolderName = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData)

            imagePath.add(Gallery(absolutePathOfImage.toString()))
        }
        return imagePath
    }
}
