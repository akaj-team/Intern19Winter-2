package asiantech.internship.summer.service_broadcastReceiver

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_main_screen_music.*
import java.util.concurrent.TimeUnit

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
        btnNextMain.setOnClickListener {
            musicService.playNext()
        }
        handleSeekBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }
    private fun setImage(){
        imgMusic.setImageURI(music[position].image)
    }
    private fun startRotatingImage() {
        val startRotateAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_rotate)
        imgMusic.startAnimation(startRotateAnimation)
    }


    private fun handleSeekBar() {
        seekBar.max = music[position].duration
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
                    tvDuration.text = toMin(music[position].duration.toLong())
                    tvCurrentDuration.text = toMin(currentDuration.toLong())
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

    internal fun toMin(millis: Long): String {
        return resources.getString(R.string.tv_duration, TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }
}
