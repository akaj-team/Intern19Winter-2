package asiantech.internship.summer.service_broadcastreceiver

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import kotlinx.android.synthetic.`at-nguyenha`.activity_list_song.*

@Suppress("DEPRECATION")
class ListMusicActivity : AppCompatActivity() {

    private var listMusic: ArrayList<MusicModel> = arrayListOf()
    private var adapter = MusicAdapter(listMusic)
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var musiURI: Uri
    private var isPlaying = false
    private var position: Int = 0
    private lateinit var imgMusicPlaying: Uri

    companion object{
        private const val REQUEST_CODE = 1000
    }

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_song)
        initView()
        initAdapter()
        initPlayPause()

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
        } else initData()
    }

    private fun initView() {
        recyclerMusicActivity.layoutManager = LinearLayoutManager(this)

    }

    @SuppressLint( "Recycle", "InlinedApi")
    private fun initData(){
        val musicCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null)
        while (musicCursor != null && musicCursor.moveToNext()) {
            val musicName = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val musicArtist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val musicDuration = musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val albumId : Long = musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val sArtWorkUri : Uri = Uri.parse("content://media/external/audio/albumart")
            val data = Uri.parse(musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)))
            val albumArtUri : String = ContentUris.withAppendedId(sArtWorkUri, albumId).toString()
            musiURI = data
            imgMusicPlaying = Uri.parse(albumArtUri)
            listMusic.add(MusicModel(data.toString(), musicName, musicArtist, albumArtUri, musicDuration))
        }
    }

    private fun initAdapter() {
        recyclerMusicActivity.adapter = adapter
        adapter.onItemClicked = {
            position = it
            val uriSong = listMusic[it].path
            if (!isPlaying) {
                playMusic(Uri.parse(uriSong))
                insertImage(Uri.parse(listMusic[it].musicImage))
                isPlaying = true
            } else {
                mediaPlayer.release()
                playMusic(Uri.parse(uriSong))
                insertImage(Uri.parse(listMusic[it].musicImage))
            }
        }
        imgNext.setOnClickListener {
            position++
            if (position > listMusic.size - 1) position = 0
            mediaPlayer.release()
            playMusic(Uri.parse(listMusic[position].path))

        }
        imgPrevious.setOnClickListener {
            position--
            if (position < listMusic.size - 1) listMusic.size - 1
            mediaPlayer.release()
            playMusic(Uri.parse(listMusic[position].path))
        }
    }

    private fun insertImage(uriArt : Uri){
        imgPlaying.setImageURI(uriArt)
        tvPlaying.text = listMusic[position].musicName
    }

    private fun playMusic(uri: Uri) {
        mediaPlayer = MediaPlayer.create(this, uri)
        mediaPlayer.start()
        isPlaying = (true)
    }

    private fun initPlayPause() {
        imgPlayButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                imgPlayButton.setImageResource(R.drawable.ic_play)
            } else {
                imgPlayButton.setImageResource(R.drawable.ic_pause)
                mediaPlayer.start()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Successes", Toast.LENGTH_LONG).show()
                initData()
            }
        }
    }
}
