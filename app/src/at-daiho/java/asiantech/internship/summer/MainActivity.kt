package asiantech.internship.summer

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.helper.Utils
import asiantech.internship.summer.model.Song
import kotlinx.android.synthetic.`at-daiho`.activity_main.*

class MainActivity : AppCompatActivity() {

    val musicService = Intent(this, MediaPlayerService::class.java)

    companion object {
        private const val STORAGE_PERMISSION_ID = 0
    }

    private lateinit var adapter: SongsAdapter
    private var songs: MutableList<Song> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        miniPlayerContainer.visibility = View.GONE
        initAdapter()
        if (checkStorePermission()) {
            getSongs()
        } else {
            showRequestPermission()
        }
    }

    private fun initAdapter() {
        adapter = SongsAdapter(songs)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.onItemClicked = {
            val song = songs[it]
            playAudio(song.path)
        }
        startService(musicService)
    }

    private fun reloadData() {
        adapter.notifyDataSetChanged()
        val frm = MiniPlayerFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.miniPlayerContainer, frm)
        miniPlayerContainer.visibility = View.VISIBLE
        transaction.commit()
    }

    private fun showRequestPermission() {
        Utils.requestPermission(this, STORAGE_PERMISSION_ID, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun checkStorePermission(): Boolean {
        return Utils.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
            val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val albumId: Long = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val sArtWorkUri: Uri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri: Uri = ContentUris.withAppendedId(sArtWorkUri, albumId)
            songs.add(Song(title, artist, duration, albumArtUri, ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)))
        }
        cursor?.close()
        reloadData()
    }

    private fun playAudio(media: Uri) { //Check is service is active
        val playerIntent = Intent(this, MediaPlayerService::class.java)
        playerIntent.putExtra("media", media.toString())
        startService(playerIntent)
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_ID && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getSongs()
        }
    }
}

