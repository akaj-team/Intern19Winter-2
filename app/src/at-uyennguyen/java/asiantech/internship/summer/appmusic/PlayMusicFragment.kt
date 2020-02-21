package asiantech.internship.summer.appmusic

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_play_music.*

class PlayMusicFragment : Fragment() {
    private lateinit var listSong: ArrayList<Media>
    private var position: Int = 0
    private lateinit var media: Media
    private var boundService: Boolean = false
    private lateinit var playMusicService: PlayMusicService
    private var isPlay: Boolean = false

    companion object {
        var MUSICLIST = "musiclist"
        var MUSICITEMPOSITION = "pos"
        const val LISTSONG = "name"
        const val POSITION = "position"
        const val ISPLAY = "isplay"
        fun newInstance(listSong: ArrayList<Media>, position: Int, isplay : Boolean ): PlayMusicFragment {
            val playMusicFragment = PlayMusicFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(LISTSONG, listSong)
            bundle.putInt(POSITION, position)
            bundle.putBoolean(ISPLAY, isplay)
            playMusicFragment.arguments = bundle
            return playMusicFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listSong = arguments?.getParcelableArrayList<Media>(LISTSONG) as ArrayList<Media>
        position = arguments?.getInt(POSITION, 0) ?: 0
        isPlay = arguments?.getBoolean(ISPLAY)!!
//        val intent = Intent(context, PlayMusicService::class.java)
//        intent.putParcelableArrayListExtra(MUSICLIST, listSong)
//        intent.putExtra(MUSICITEMPOSITION, position)
//        context?.startService(intent)
        return inflater.inflate(R.layout.fragment_play_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isPlay==false){
            btnPlay.setImageResource(R.drawable.ic_play)
        }
        else{
            btnPlay.setImageResource(R.drawable.ic_pause)
        }
        imgThumbnailPlay.setImageURI(Uri.parse(listSong[position].thumbnail))
        if(imgThumbnailPlay.drawable==null){
            imgThumbnailPlay.setImageResource(R.drawable.ic_music_note)
        }
        tvCurrentPosition.text = listSong[position].time
        tvNamePlay.text = listSong[position].nameSong
        tvSingerPlay.text = listSong[position].singer
        val intent = Intent(context, PlayMusicService::class.java)
        intent.putParcelableArrayListExtra(MUSICLIST, listSong)
        intent.putExtra(MUSICITEMPOSITION, position)
        context?.bindService(intent, serviceConnection, BIND_AUTO_CREATE)

        btnPlay.setOnClickListener {
            if (!isPlay) {
                playMusicService.pauseMusic()
                btnPlay.setImageResource(R.drawable.ic_pause)
                isPlay = true
            } else {
                btnPlay.setImageResource(R.drawable.ic_play)
                playMusicService.runContinueMusic()
                isPlay = false
            }
        }

        btnNext.setOnClickListener {
            isPlay = false
            val musicPos = position + 1
            imgThumbnailPlay.setImageURI(Uri.parse(listSong[musicPos].thumbnail))
            if(imgThumbnailPlay.drawable==null){
                imgThumbnailPlay.setImageResource(R.drawable.ic_music_note)
            }
            tvCurrentPosition.text = listSong[musicPos].time
            tvNamePlay.text = listSong[musicPos].nameSong
            tvSingerPlay.text = listSong[musicPos].singer
            position = musicPos
            btnPlay.setImageResource(R.drawable.ic_play)
            playMusicService.nextMusic()
        }

        btnPrevious.setOnClickListener {
            isPlay = false
            val musicPos = position - 1
            imgThumbnailPlay.setImageURI(Uri.parse(listSong[musicPos].thumbnail))
            if(imgThumbnailPlay.drawable==null){
                imgThumbnailPlay.setImageResource(R.drawable.ic_music_note)
            }
            tvCurrentPosition.text = listSong[musicPos].time
            tvNamePlay.text = listSong[musicPos].nameSong
            tvSingerPlay.text = listSong[musicPos].singer
            position = musicPos
            btnPlay.setImageResource(R.drawable.ic_play)
            playMusicService.previousMusic()
        }
    }

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            boundService = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder: PlayMusicService.MediaBinder = service as PlayMusicService.MediaBinder
            playMusicService = binder.getService()
            boundService = true
        }
    }

    fun convertDuration(duration: Long): String? {
        var out: String? = null
        var hours: Long = 0
        try {
            hours = (duration / 3600000).toLong()
        } catch (e: Exception) {
            e.printStackTrace()
            return out
        }

        val remainingMinutes = (duration - hours * 3600000) / 60000
        var minutes = remainingMinutes.toString()
        if (minutes.equals(0)) {
            minutes = "00"
        }
        val remaining_seconds = duration - hours * 3600000 - remainingMinutes * 60000
        var seconds = remaining_seconds.toString()
        if (seconds.length < 2) {
            seconds = "00"
        } else {
            seconds = seconds.substring(0, 2)
        }

        if (hours > 0) {
            out = "$hours:$minutes:$seconds"
        } else {
            out = "$minutes:$seconds"
        }

        return out
    }
}
