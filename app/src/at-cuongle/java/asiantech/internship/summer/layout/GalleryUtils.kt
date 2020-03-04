package asiantech.internship.summer.layout

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore

object GalleryUtils {
    private var imagePath = mutableListOf<Gallery>()
    private var imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private var column_index_data = 0
    private var column_index_folder_name: Int = 0
    private var absolutePathOfImage: String? = null
    @SuppressLint("InlinedApi")
    private var projection = arrayOf(MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

    @SuppressLint("Recycle", "InlinedApi")
    internal fun getImage(context: Context): MutableList<Gallery> {
        val cursor = context.contentResolver.query(imageUri, projection, null, null, null)
        column_index_data = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)!!
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)

            imagePath.add(Gallery(absolutePathOfImage.toString()))
        }
        return imagePath
    }
}
