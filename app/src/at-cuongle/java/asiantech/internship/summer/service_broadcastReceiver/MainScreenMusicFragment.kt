package asiantech.internship.summer.service_broadcastReceiver

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_main_screen_music.*

class MainScreenMusicFragment : Fragment() {

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
        imgMusic.setImageURI(music[position].image)
        btnNextMain.setOnClickListener {
            musicService.playNext()
        }
        handleSeekBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }

    private fun startRotatingImage() {
        val startRotateAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_rotate)
        imgMusic.startAnimation(startRotateAnimation)
    }


    private fun handleSeekBar() {
        seekBar.max = music[position].duration
        tvDuration.text = music[position].duration.toString()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
//                mediaPlayer.seekTo(seekBar.progress)
            }
        })
        runnable = object : Runnable {
            override fun run() {
                val currentPosition = musicService.getCurrentDuration()
                Log.i("XXX", "aaaaaa${currentPosition.toString()}")
                if (currentPosition != null) {
                    seekBar.progress = currentPosition
                    tvCurrentDuration.text = currentPosition.toString()
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

}
