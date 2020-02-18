package asiantech.internship.summer.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song
import asiantech.internship.summer.service.model.SongUtils
import kotlinx.android.synthetic.`at-hauha`.activity_music.*

class MusicActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private var isPlaying = false
    private val songList = mutableListOf<Song>()
    private lateinit var adapter: MusicAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        checkPermission()
        initAdapter(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            getSongList()
        }
    }

    private fun getSongList() {
        songList.clear()
        songList.addAll(SongUtils.getSongDevices(this))
    }

    private fun initAdapter(context: Context) {
        checkPermission()
        adapter = MusicAdapter(songList, context)
        adapter.onItemClicked = {
            playMusic(songList[it])

        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun startMusic(uri: Uri){
        mediaPlayer = MediaPlayer.create(this, uri)
        mediaPlayer.start()
    }

    private fun playMusic(song: Song) {
        tvSongName.text = song.title
        val bitmap = SongUtils.songArt(Uri.parse(song.path), this)
        imgSongPlay.setImageBitmap(bitmap)
    }
}