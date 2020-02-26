@file:Suppress("DEPRECATION")

package asiantech.internship.summer.service_broadcastreceiver.model

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import java.util.concurrent.TimeUnit

object Units {

    private var listMusic: ArrayList<MusicModel> = arrayListOf()
    const val ACTION_PLAY_PAUSE = "playpause"
    const val ACTION_SKIP_NEXT = "skipnext"
    const val ACTION_PREVIOUS = "previous"
    const val ACTION_KILL_MEDIA = "killmedia"


    fun convertTimeMusic(millis: Int): String {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis.toLong())))
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


    @SuppressLint("Recycle", "InlinedApi")
    fun insertData(context: Context): ArrayList<MusicModel> {
        listMusic.clear()
        val musicCursor: Cursor? = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null)
        while (musicCursor != null && musicCursor.moveToNext()) {
            val musicName = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val musicArtist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val musicDuration = musicCursor.getInt(musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val albumId: Long = musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val sArtWorkUri: Uri = Uri.parse("content://media/external/audio/albumart")
            val data = Uri.parse(musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)))
            val albumArtUri: String = ContentUris.withAppendedId(sArtWorkUri, albumId).toString()
            listMusic.add(MusicModel(data.toString(), musicName, musicArtist, albumArtUri, musicDuration))
        }
        return listMusic
    }
}
