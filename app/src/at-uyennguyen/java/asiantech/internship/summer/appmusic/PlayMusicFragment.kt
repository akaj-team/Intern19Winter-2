package asiantech.internship.summer.appmusic

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_play_music.*

class PlayMusicFragment : Fragment() {
    private lateinit var listSong: ArrayList<Media>
    private var position: Int = 0
    private var musicPos: Int = 0
    private var boundService: Boolean = false
    private var playMusicService: PlayMusicService? = null
    private var isPlay: Boolean = false
    private var handler = Handler()
    private var appNotification: AppNotification? = null
//    private var rotateAnimation : Animation? = AnimationUtils.loadAnimation(,R.anim.animation)

    companion object {
        var MUSICLIST = "musiclist"
        var MUSICITEMPOSITION = "pos"
        const val LISTSONG = "name"
        const val POSITION = "position"
        const val ISPLAY = "isplay"
        fun newInstance(listSong: ArrayList<Media>, position: Int, isplay: Boolean): PlayMusicFragment {
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
        return inflater.inflate(R.layout.fragment_play_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seekBar()
        if (isPlay == false) {
//            imgThumbnailPlay.startAnimation(rotateAnimation)
            btnPlay.setImageResource(R.drawable.ic_pause_white_36dp)
        } else {
            btnPlay.setImageResource(R.drawable.ic_play_circle)
        }
        imgThumbnailPlay.setImageURI(Uri.parse(listSong[position].thumbnail))
        if (imgThumbnailPlay.drawable == null) {
            imgThumbnailPlay.setImageResource(R.drawable.ic_music_note_white_36dp)
        }
        tvNamePlay.text = listSong[position].nameSong
        tvSingerPlay.text = listSong[position].singer
        val intent = Intent(context, PlayMusicService::class.java)
        intent.putParcelableArrayListExtra(MUSICLIST, listSong)
        intent.putExtra(MUSICITEMPOSITION, position)
        context?.bindService(intent, serviceConnection, BIND_AUTO_CREATE)

        btnPlay.setOnClickListener {
            if (!isPlay) {
                playMusicService?.pauseMusic()
                btnPlay.setImageResource(R.drawable.ic_play_circle)
                isPlay = true
            } else {
                btnPlay.setImageResource(R.drawable.ic_pause_white_36dp)
                playMusicService?.runContinueMusic()
                isPlay = false
            }
            appNotification = playMusicService?.let { it1 -> AppNotification(it1) }
            val notification = appNotification?.createNotifi(listSong[musicPos], isPlay)
            playMusicService?.startForeground(1, notification)
        }

        btnNext.setOnClickListener {
            isPlay = false
            if (musicPos == listSong.size - 1) {
                musicPos = 0
            } else {
                musicPos = position + 1
            }
            appNotification = playMusicService?.let { it1 -> AppNotification(it1) }
            val notification = appNotification?.createNotifi(listSong[musicPos], isPlay)
            playMusicService?.startForeground(1, notification)
            imgThumbnailPlay.setImageURI(Uri.parse(listSong[musicPos].thumbnail))
            if (imgThumbnailPlay.drawable == null) {
                imgThumbnailPlay.setImageResource(R.drawable.ic_music_note_white_36dp)
            }
            tvNamePlay.text = listSong[musicPos].nameSong
            tvSingerPlay.text = listSong[musicPos].singer
            position = musicPos
            btnPlay.setImageResource(R.drawable.ic_pause_white_36dp)
            playMusicService?.nextMusic()
        }

        btnPrevious.setOnClickListener {
            isPlay = false
            if (musicPos <= 0) {
                musicPos = listSong.size - 1
            } else {
                musicPos = position - 1
            }
            appNotification = playMusicService?.let { it1 -> AppNotification(it1) }
            val notification = appNotification?.createNotifi(listSong[musicPos], isPlay)
            playMusicService?.startForeground(1, notification)
            imgThumbnailPlay.setImageURI(Uri.parse(listSong[musicPos].thumbnail))
            if (imgThumbnailPlay.drawable == null) {
                imgThumbnailPlay.setImageResource(R.drawable.ic_music_note_white_36dp)
            }
            tvNamePlay.text = listSong[musicPos].nameSong
            tvSingerPlay.text = listSong[musicPos].singer
            position = musicPos
            btnPlay.setImageResource(R.drawable.ic_pause_white_36dp)
            playMusicService?.previousMusic()
        }

    }

    override fun onPause() {
        super.onPause()
        activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, ListMusicFragment.newInstance(position, isPlay))
                ?.commit()
    }

    private fun seekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    playMusicService?.seekTo(seekBar.progress)
                }
            }

        })
        val runnable = object : Runnable {
            override fun run() {
                val currenPosision = playMusicService?.getCurrentPosition()
                if (currenPosision != null) {
                    seekBar?.progress = currenPosision
                    tvDuration?.text = convertDuration(listSong[position].time.toLong())
                    tvCurrentPosition?.text = convertDuration(currenPosision.toString().toLong())
                }
                handler.postDelayed(this, 100)
                seekBar?.max = listSong[position].time.toInt()
            }
        }
        handler.post(runnable)
    }


    private var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            boundService = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder: PlayMusicService.MediaBinder = service as PlayMusicService.MediaBinder
            playMusicService = binder.getService()
            boundService = true
            service.getService().onItemClicked = {
                Log.i("vinh", "music position: " + it)
            }
        }
    }

    fun convertDuration(duration: Long): String? {
        var out: String? = null
        val hours: Long
        try {
            hours = (duration / 3600000)
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
