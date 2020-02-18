package asiantech.internship.summer.service_broadcastreceiver

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.activity_list_song.*

class ListMusicActivity : AppCompatActivity() {

    private var listMusic: MutableList<MusicModel> = mutableListOf()
    private var adapter = MusicAdapter(listMusic)

    companion object{
        private const val REQUEST_CODE = 1000
    }

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_song)

        recyclerMusicActivity.layoutManager = LinearLayoutManager(this)
        recyclerMusicActivity.adapter = adapter

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
        } else initData()
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
            val albumArtUri : Uri = ContentUris.withAppendedId(sArtWorkUri, albumId)
            listMusic.add(MusicModel(musicName, musicArtist,albumArtUri, musicDuration))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Successes",Toast.LENGTH_LONG).show()
                initData()
            }
        }
    }
}
