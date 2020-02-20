package asiantech.internship.summer

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.helper.Utils
import asiantech.internship.summer.model.Song
import kotlinx.android.synthetic.`at-daiho`.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val STORAGE_PERMISSION_ID = 0
    }

    private var songs: MutableList<Song> = mutableListOf()
    private lateinit var adapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        if (checkStorePermission(STORAGE_PERMISSION_ID)) {
            getSongs()
        } else {
            showRequestPermission(STORAGE_PERMISSION_ID)
        }
    }

    private fun initAdapter() {
        adapter = SongsAdapter(songs)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun reloadData() {
        adapter.notifyDataSetChanged()
    }

    private fun showRequestPermission(requestCode: Int) {
        Utils.requestPermission(this, requestCode, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun checkStorePermission(permission: Int): Boolean {
        return if (permission == STORAGE_PERMISSION_ID) {
            Utils.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_ID && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getSongs()
        }
    }

    private fun getSongs() {
        // Get the external storage media store audio uri
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        // IS_MUSIC : Non-zero if the audio file is music
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"

        // Sort
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"

        val cursor: Cursor? = contentResolver.query(uri, null, selection, null, sortOrder)
        while (cursor != null && cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val albumId: Long = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val sArtWorkUri: Uri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri: Uri = ContentUris.withAppendedId(sArtWorkUri, albumId)
            songs.add(Song(title, artist, duration, albumArtUri))
        }
        cursor?.close()
        reloadData()
    }
}

