package asiantech.internship.summer.service_broadcastReceiver

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import asiantech.internship.summer.R
import java.util.concurrent.TimeUnit

object MusicData {
    private val musicData = mutableListOf<Music>()
    fun getMusic(context: Context): MutableList<Music> {
        val songCursor = context.contentResolver?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
        val idColumn = songCursor?.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        while (songCursor != null && songCursor.moveToNext()) {
            val id = idColumn?.let { songCursor.getLong(it) }
            val songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val songDuration = songCursor.getInt(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val songArtist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val uri: Uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id!!)
            val albumId: Long? = songCursor.getLong(songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId!!)
            musicData.apply {
                add(Music(songDuration, songName, uri, songArtist, albumArtUri))
            }
        }
        return musicData
    }

    internal fun convertUriToBitmap(path: Uri, context: Context): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    internal fun toMin(millis: Long, context: Context): String {
        return context.getString(R.string.tv_duration, TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }
}
