package asiantech.internship.summer.appmusic

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_list_music.*

@Suppress("DEPRECATION")
class ListMusicFragment : Fragment() {
    private var isPlay: Boolean = false
    private lateinit var playMusicService: PlayMusicService
    private var position: Int = 0
    private var musicPos: Int = 0
    lateinit var listMusicAdapter: ListMusicAdapter
    private var listMedia = arrayListOf<Media>()
    private var notification: Notification? = null

    companion object {
        private var READ_CODE_REQUEST = 111
        private const val ISPLAY = "isplay"
        private const val POSITION = "position"
        fun newInstance(position: Int, isplay: Boolean): ListMusicFragment {
            val listMusicFragment = ListMusicFragment()
            val bundle = Bundle()
            bundle.putBoolean(ISPLAY, isplay)
            bundle.putInt(POSITION, position)
            listMusicFragment.arguments = bundle
            return listMusicFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listMusicAdapter = listMedia.let { context?.let { it1 -> ListMusicAdapter(it, it1) } }!!
        if (context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), READ_CODE_REQUEST)
        } else {
            loadMedia()
        }
        listMusicAdapter.onItemClicked = {
            val intent = Intent(context, PlayMusicService::class.java)
            intent.putParcelableArrayListExtra(PlayMusicFragment.MUSICLIST, listMedia)
            intent.putExtra(PlayMusicFragment.MUSICITEMPOSITION, it)
            context?.startService(intent)
            notification = Notification(playMusicService)
            val notification = notification?.createNotifi(listMedia[it], isPlay)
            playMusicService.startForeground(1, notification)
            listMedia[it].run {
                position = it
                musicPos = position
                imgBottomPlay.setImageResource(R.drawable.ic_pause_white_36dp)
                tvBottomName.text = listMedia[it].nameSong
                tvBottomSinger.text = listMedia[it].singer
                imgBottomThumbnail.setImageURI(Uri.parse(listMedia[it].thumbnail))
                if (imgBottomThumbnail.drawable == null) {
                    imgBottomThumbnail.setImageResource(R.drawable.ic_music_note_white_36dp)
                }
            }
            context?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        imgBottomPlay.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isPlay) {
                    playMusicService.pauseMusic()
                    imgBottomPlay.setImageResource(R.drawable.ic_play_circle)
                    isPlay = true
                } else {
                    imgBottomPlay.setImageResource(R.drawable.ic_pause_white_36dp)
                    playMusicService.runContinueMusic()
                    isPlay = false
                }
                notification = Notification(playMusicService)
                val notification = notification?.createNotifi(listMedia[musicPos], isPlay)
                playMusicService.startForeground(1, notification)
            }

        })
        imgBottomNext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isPlay = false
                if (musicPos == listMedia.size - 1) {
                    musicPos = 0
                } else {
                    musicPos = position + 1
                }
                notification = Notification(playMusicService)
                val notification = notification?.createNotifi(listMedia[musicPos], isPlay)
                playMusicService.startForeground(1, notification)
                imgBottomThumbnail.setImageURI(Uri.parse(listMedia[musicPos].thumbnail))
                if (imgBottomThumbnail.drawable == null) {
                    imgBottomThumbnail.setImageResource(R.drawable.ic_music_note_white_36dp)
                }
                tvBottomName.text = listMedia[musicPos].nameSong
                tvBottomSinger.text = listMedia[musicPos].singer
                position = musicPos
                imgBottomPlay.setImageResource(R.drawable.ic_pause_white_36dp)
                playMusicService.nextMusic()
            }
        })
        imgBottomPrevious.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isPlay = false
                if (musicPos <= 0) {
                    musicPos = listMedia.size - 1
                } else {
                    musicPos = position - 1
                }
                notification = Notification(playMusicService)
                val notification = notification?.createNotifi(listMedia[musicPos], isPlay)
                playMusicService.startForeground(1, notification)
                imgBottomThumbnail.setImageURI(Uri.parse(listMedia[musicPos].thumbnail))
                if (imgBottomThumbnail.drawable == null) {
                    imgBottomThumbnail.setImageResource(R.drawable.ic_music_note_white_36dp)
                }
                tvBottomName.text = listMedia[musicPos].nameSong
                tvBottomSinger.text = listMedia[musicPos].singer
                position = musicPos
                imgBottomPlay.setImageResource(R.drawable.ic_pause_white_36dp)
                playMusicService.previousMusic()
            }
        })
        contrainLayoutPlay.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayout, PlayMusicFragment.newInstance(listMedia, position, isPlay))
                        ?.addToBackStack(null)
                        ?.commit()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == READ_CODE_REQUEST && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadMedia()
        }
    }

    override fun onResume() {
        super.onResume()

        val pos = arguments?.getInt(POSITION)
        val play: Boolean? = arguments?.getBoolean(ISPLAY)
        if (play != null) {
            isPlay = play
        }
        if (pos != null) {
            position = pos
        }
        if (isPlay) {
            imgBottomPlay.setImageResource(R.drawable.ic_play_circle)
            isPlay = true
        } else {
            imgBottomPlay.setImageResource(R.drawable.ic_pause_white_36dp)
            isPlay = false
        }
        tvBottomName.text = listMedia[position].nameSong
        tvBottomSinger.text = listMedia[position].singer
        imgBottomThumbnail.setImageURI(Uri.parse(listMedia[position].thumbnail))
        if (imgBottomThumbnail.drawable == null) {
            imgBottomThumbnail.setImageResource(R.drawable.ic_music_note_white_36dp)
        }
        val intent = Intent(context, PlayMusicService::class.java)
        intent.putParcelableArrayListExtra(PlayMusicFragment.MUSICLIST, listMedia)
        intent.putExtra(PlayMusicFragment.MUSICITEMPOSITION, position)
        context?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        contrainLayoutPlay.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayout, PlayMusicFragment.newInstance(listMedia, position, isPlay))
                        ?.addToBackStack(null)
                        ?.commit()
            }

        })
    }

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder: PlayMusicService.MediaBinder = service as PlayMusicService.MediaBinder
            playMusicService = binder.getService()
        }
    }

    @SuppressLint("Recycle")
    private fun loadMedia() {
        val songCursor = context?.contentResolver?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
        while (songCursor != null && songCursor.moveToNext()) {
            val songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val songTime = songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            var songSinger = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val albumId: Long? = songCursor.getLong(songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            val songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(sArtworkUri,
                    albumId!!)
            if (songSinger == "<unknown>") {
                songSinger = ""
            }
            listMedia.add(Media(songName, songSinger, songTime.toString(), albumArtUri.toString(), songPath))
        }
        listMusicAdapter = listMedia.let { context?.let { it1 -> ListMusicAdapter(it, it1) } }!!
        recyclerView.adapter = listMusicAdapter
    }
}
