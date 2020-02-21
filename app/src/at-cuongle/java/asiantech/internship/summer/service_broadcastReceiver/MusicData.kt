package asiantech.internship.summer.service_broadcastReceiver

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

object MusicData {
    private val musicData = mutableListOf<Music>()
    @SuppressLint("Recycle", "InlinedApi")
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
}
