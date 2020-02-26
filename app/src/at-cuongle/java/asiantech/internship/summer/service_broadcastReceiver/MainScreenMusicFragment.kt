package asiantech.internship.summer.service_broadcastReceiver

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-cuongle`.fragment_main_screen_music.*

class MainScreenMusicFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val DELAY_TIME: Long = 1000
        private const val ARG_POSITION = "name"
        fun newInstance(position: Int) = MainScreenMusicFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, position)
            }
        }
    }

    val handler = Handler()
    private lateinit var runnable: Runnable
    private var musicService = ForegroundService()
    private var musicBound = false
    private var position = 0
    private var isPlaying = false
    private val music = mutableListOf<Music>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_screen_music, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
    }

    private var musicConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            musicBound = false
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as ForegroundService.LocalBinder
            musicService = binder.getService
            btnPausePlay.isSelected = musicService.isPlaying()
            isPlaying = musicService.isPlaying()
            musicBound = true
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(context, ForegroundService::class.java)
        context?.bindService(intent, musicConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startRotatingImage()
        initData()
        btnNextMain.setOnClickListener(this)
        btnPreviousMain.setOnClickListener(this)
        btnPausePlay.setOnClickListener(this)
        btnShuffle.setOnClickListener(this)
        btnLoop.setOnClickListener(this)
        handleSeekBar()
    }

    override fun onClick(view: View?) {
        when (view) {
            btnNextMain -> {
                sendAction(MusicAction.NEXT)
            }
            btnPausePlay -> {
                onPausePlayMusic()
            }
            btnPreviousMain -> {
                sendAction(MusicAction.PREVIOUS)
            }
            btnShuffle -> {
                sendAction(MusicAction.SHUFFLE)
            }
            btnLoop -> {
                sendAction(MusicAction.LOOP)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }

    private fun setImage() {
        imgMusic.setImageURI(music[position].image)
        Glide.with(requireContext())
                .load(music[position].image)
                .placeholder(R.drawable.icon_song)
                .into(imgMusic)
    }

    private fun sendAction(action: String) {
        val intent = Intent(requireContext(), ForegroundService::class.java)
        intent.action = action
        intent.putExtra(action, "1")
        context?.startService(intent)
    }

    private fun startRotatingImage() {
        val startRotateAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_rotate)
        imgMusic.startAnimation(startRotateAnimation)
    }

    private fun handleSeekBar() {
        seekBar.max = music[position].duration
        btnPausePlay.isSelected = true

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                musicService.seekTo(seekBar.progress)
            }
        })
        runnable = object : Runnable {
            override fun run() {
                position = musicService.getPosition()
                setImage()
                val currentDuration = musicService.getCurrentDuration()
                if (currentDuration != null) {
                    seekBar.progress = currentDuration
                    tvDuration.text = MusicData.toMin(music[position].duration.toLong(), requireContext())
                    tvCurrentDuration.text = MusicData.toMin(currentDuration.toLong(), requireContext())
                }
                handler.postDelayed(this, DELAY_TIME)
            }
        }
        handler.post(runnable)
    }

    private fun initData() {
        music.clear()
        music.addAll(MusicData.getMusic(requireContext()))
    }

    private fun onPausePlayMusic() {
        isPlaying = if (!isPlaying) {
            sendAction(MusicAction.PLAY)
            btnPausePlay.isSelected = true
            true
        } else {
            sendAction(MusicAction.PAUSE)
            btnPausePlay.isSelected = false
            false
        }
    }
}
