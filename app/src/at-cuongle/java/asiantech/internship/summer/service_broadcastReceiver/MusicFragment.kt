package asiantech.internship.summer.service_broadcastReceiver

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.MainActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_list_music.*
import java.text.FieldPosition


class MusicFragment : Fragment() {
    companion object {
        private const val PERMISSION_CODE = 101
    }

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var isPlaying = false
    private var handler: Handler = Handler()
    private val music = mutableListOf<Music>()
    private lateinit var musicAdapter: MusicAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicAdapter = MusicAdapter(music)
        requestPermission()
        initAdapter()
        getMusic()
        initListeners()
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter(music)
        rvMusic.adapter = musicAdapter
    }

    private fun initListeners() {
        var positionMusicPlaying = 0
        musicAdapter.onSongClicked = {
            positionMusicPlaying = it
            Toast.makeText(context, music[it].name, Toast.LENGTH_SHORT).show()
            if (isPlaying) {
                mediaPlayer.release()
            }
            playMusic(it)
        }
        onPausePlayMusic()
        btnNext.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.release()
                playMusic(positionMusicPlaying++)
//                if (positionMusicPlaying == music.size-1){
//                    playMusic(0)
//                }
            }
        }
        cardViewBottom.setOnLongClickListener {
            (activity as? ServiceBroadCastActivity)?.replaceFragment(MainScreenMusicFragment())
            true
        }

    }

    //    private fun playMusic(uriMusic: Uri) {
//        mediaPlayer = MediaPlayer.create(context, uriMusic)
//        mediaPlayer.start()
//        isPlaying = true
//        btnPP.isSelected = true
//    }
    private fun playMusic(position: Int) {
        mediaPlayer = MediaPlayer.create(context, music[position].uri)
        mediaPlayer.start()
        Log.i("XXX", "position:${position}")
        imgMusicBottom.setImageURI(music[position].image)
        tvNameMusicBottom.text = music[position].name
        tvArtistBottom.text = music[position].artist
        isPlaying = true
        btnPP.isSelected = true
    }

    private fun onPausePlayMusic() {
        btnPP.setOnClickListener {
            isPlaying = if (!isPlaying) {
                mediaPlayer.start()
                btnPP.isSelected = true
                true
            } else {
                btnPP.isSelected = false
                mediaPlayer.pause()
                false
            }
        }
    }

//    private fun nextMusic(position: Int) {
//        btnNext.setOnClickListener {
//            mediaPlayer.release()
//            Log.i("XXX", "$position")
//            playMusic(music[position + 1].uri)
//        }
//    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let { context?.let { context -> ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) } }
                    == PackageManager.PERMISSION_DENIED ||
                    this.let {
                        context?.let { context ->
                            ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        }
                    }
                    == PackageManager.PERMISSION_DENIED) {
                val permission =
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
            }
        } else {
        }
    }

    @SuppressLint("InlinedApi", "Recycle")
    private fun getMusic() {
        val songCursor = context?.contentResolver?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
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
            music.apply {
                add(Music(songDuration, songName, uri, songArtist, albumArtUri))
            }
            musicAdapter.notifyDataSetChanged()
        }
    }
}
