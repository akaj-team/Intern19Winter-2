package asiantech.internship.summer.service

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import asiantech.internship.summer.service.model.Song

object Utils {
    private const val TITLE = 0
    private const val ARTIST_ID = 1
    private const val ARTIST = 2
    private const val PATH = 3
    private const val DURATION = 4
    const val SONGLIST = "songList"
    const val POSITION = "position"
    const val PLAYSONG = 1
    const val DEFAUlT_POS = 0
    private const val SHUFFLE = "KEY_SHUFFLE"
    private const val REPEAT = "KEY_REPEAT"
    private const val PREFERENCE = "MyMusic"
    private val MUSIC_URI: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    private val allDeviceSong = ArrayList<Song>()

    private val cursorCols = arrayOf(
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ARTIST_ID,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DURATION
    )

    fun getSongDevices(context: Context): MutableList<Song> {
        var cursor: Cursor? = context.contentResolver?.query(MUSIC_URI, cursorCols, null, null, null)
        cursor?.let {
            if (it != null && it.moveToFirst()) {
                while (!it.isAfterLast) {
                    allDeviceSong.add(getSongCursor(it))
                    it.moveToNext()
                }
            }
        }
        cursor?.close()
        return allDeviceSong
    }

    fun songArt(path: Uri, context: Context): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    fun convertTime(milliseconds: Int): String {
        return (String.format("%02d", milliseconds / 1000 / 60) + ":" + String.format("%02d", milliseconds / 1000 % 60))
    }

    private fun getSongCursor(cursor: Cursor) = Song(cursor.getString(TITLE), cursor.getInt(ARTIST_ID), cursor.getString(ARTIST), cursor.getString(PATH), cursor.getInt(DURATION))

}
