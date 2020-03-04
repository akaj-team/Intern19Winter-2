package asiantech.internship.summer.savedata

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
import asiantech.internship.summer.savedata.model.Picture

object Utils {
    private var picturePath = mutableListOf<Picture>()
    private var pictureUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private var column_index_data = 0
    private var column_index_folder_name: Int = 0
    private var absolutePathOfImage: String? = null
    const val PUT_ID = "PUT_ID"
    const val SHARE_REF = "SHARE_REF"
    const val DEFAULT_ID = 0

    @SuppressLint("InlinedApi")
    private var projection = arrayOf(MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

    @SuppressLint("Recycle", "InlinedApi")
    internal fun getImage(context: Context): MutableList<Picture> {
        val cursor = context.contentResolver.query(pictureUri, projection, null, null, null)
        column_index_data = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)!!
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            picturePath.add(Picture(absolutePathOfImage.toString()))
        }
        return picturePath
    }

}