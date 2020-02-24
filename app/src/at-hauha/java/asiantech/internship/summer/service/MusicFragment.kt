package asiantech.internship.summer.service

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.service.Utils.NEXT_ACTION
import asiantech.internship.summer.service.Utils.PLAY_ACTION
import asiantech.internship.summer.service.Utils.PREV_ACTION
import asiantech.internship.summer.service.model.Song
import kotlinx.android.synthetic.`at-hauha`.fragment_music.*

class MusicFragment : Fragment() {

    companion object {
        private const val DELAY_TIME: Long = 500
        private const val ARG_POSITION = "position"
        private const val ARG_LIST = "songList"
        private const val ARG_PLAYING = "isPlaying"
        fun newInstance(mPosition: Int, songList: ArrayList<Song>, isPlaying: Boolean) = MusicFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, mPosition)
                putParcelableArrayList(ARG_LIST, songList)
                putBoolean(ARG_PLAYING, isPlaying)
            }
        }
    }

    private var notification: Notification? = null
    private var position = 0
    private var musicService = PlayMusicService()
    private var playIntent: Intent? = null
    private var musicBound = false
    private lateinit var songList: ArrayList<Song>
    private var isPlaying = false
    private var isShuffle = false
    private val mHandler = Handler()
    private var runnable = Runnable { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            position = getInt(ARG_POSITION)
            songList = getParcelableArrayList<Song>(ARG_LIST) as ArrayList<Song>
            isPlaying = getBoolean(ARG_PLAYING)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(runnable)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mHandler.removeCallbacks(runnable)
        requireContext().unregisterReceiver(broadcastReceiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val song = songList[position]
        imgPlay.isSelected = isPlaying
        imgShuffle.isSelected = false
        setMusic(song)
        updateMusic()
        imgPlay.setOnClickListener {
            pauseSong()
        }
        imgNext.setOnClickListener {
            nextMusic()
            setMusic(songList[position])
        }
        imgPrevious.setOnClickListener {
            prevMusic()
            setMusic(songList[musicService.getPosition()])
        }
        tvBack.setOnClickListener {
            (activity as? MusicActivity)?.replacePlayListFragment(musicService.getPosition(),isPlaying)
            //Toast.makeText(requireContext(),position.toString(),Toast.LENGTH_LONG).show()
        }
        imgShuffle.setOnClickListener {
            shuffleMusic()
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.apply {
            addAction(PLAY_ACTION)
            addAction(NEXT_ACTION)
            addAction(PREV_ACTION)
        }
        requireContext().registerReceiver(broadcastReceiver, filter)
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                PREV_ACTION -> {
                    prevMusic()
                    setMusic(songList[musicService.getPosition()])
                }
                PLAY_ACTION -> {
                    pauseSong()
                }
                NEXT_ACTION -> {
                    nextMusic()
                    setMusic(songList[musicService.getPosition()])
                }
            }
        }
    }

    private fun createNotification(position: Int) {
        notification = Notification(musicService)
        val notification = notification?.createNotification(songList[position], isPlaying)
        musicService.startForeground(1, notification)
    }

    private fun shuffleMusic() {
        if (!isShuffle) {
            imgShuffle.isSelected = true
            isShuffle = true
            musicService.shuffleMusic()
        } else {
            imgShuffle.isSelected = false
            isShuffle = false
        }
    }

    private var musicConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            musicBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as PlayMusicService.MusicBinder
            musicService = binder.getService
            position.let { musicService.getPosition() }
            musicBound = true
        }
    }

    override fun onStart() {
        super.onStart()
        if (playIntent == null) {
            playIntent = Intent(context, PlayMusicService::class.java)
            context?.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun setMusic(song: Song) {
        val bitmap = Utils.songArt(Uri.parse(song.path), requireContext())
        imgSong.setImageBitmap(bitmap)
        rotateImage()
        tvSongName.text = song.title
        tvDuration.text = Utils.convertTime(song.duration)
        seekBar.max = (song.duration)
    }

    private fun rotateImage() {
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        imgSong.startAnimation(rotate)
    }

    private fun nextMusic() {
        musicService.nextSong()
        createNotification(musicService.getPosition())
    }

    private fun prevMusic() {
        musicService.prevSong()
        createNotification(musicService.getPosition())
    }

    private fun pauseSong() {
        if (isPlaying) {
            musicService.pauseSong()
            isPlaying = false
            imgPlay.isSelected = false
        } else {
            musicService.playSong()
            imgPlay.isSelected = true
            isPlaying = true
        }
        createNotification(musicService.getPosition())
    }

    private fun updateMusic() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let { musicService.seekToMedia(it) }
            }
        })
        var currentPos = position
        runnable = object : Runnable {
            override fun run() {
                val currentPosition = musicService.currentPosition()
                if (position > currentPos) {
                    currentPos = position
                    setMusic(songList[currentPos])
                }
                if (currentPosition != null) {
                    seekBar.progress = currentPosition
                    position = musicService.getPosition()
                    tvStart.text = Utils.convertTime(currentPosition)
                }
                mHandler.postDelayed(this, DELAY_TIME)
            }
        }
        mHandler.post(runnable)
    }

}
