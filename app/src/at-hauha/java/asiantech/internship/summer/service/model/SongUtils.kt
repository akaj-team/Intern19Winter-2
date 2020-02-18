package asiantech.internship.summer.service.model

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore

object SongUtils {
    private const val TITLE = 0
    private const val ARTIST_ID = 1
    private const val ARTIST = 2
    private const val PATH = 3
    private const val DURATION = 4
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
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(MUSIC_URI, cursorCols, null, null, null)
        } catch (e: SecurityException) {
            cursor?.close()
        }
        return getSongs(cursor!!)
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

    private fun getSongs(cursor: Cursor): MutableList<Song> {
        val songs = ArrayList<Song>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val song = getSongCursor(cursor)
                if (song.duration >= 30000 && song.artist != "<unknown>") {
                    songs.add(song)
                    allDeviceSong.add(song)
                }
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return songs
    }

    private fun getSongCursor(cursor: Cursor) = Song(cursor.getString(TITLE), cursor.getInt(ARTIST_ID), cursor.getString(ARTIST), cursor.getString(PATH), cursor.getInt(DURATION))

}
