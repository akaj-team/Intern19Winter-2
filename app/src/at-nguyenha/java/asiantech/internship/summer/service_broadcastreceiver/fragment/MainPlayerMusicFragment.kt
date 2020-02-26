package asiantech.internship.summer.service_broadcastreceiver.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
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
import asiantech.internship.summer.service_broadcastreceiver.Notification
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import asiantech.internship.summer.service_broadcastreceiver.model.Units
import asiantech.internship.summer.service_broadcastreceiver.service.PlayMusicService
import asiantech.internship.summer.service_broadcastreceiver.service.PlayMusicService.Companion.isReNew
import asiantech.internship.summer.service_broadcastreceiver.service.PlayMusicService.Companion.isShuffle
import kotlinx.android.synthetic.`at-nguyenha`.activity_main_player_music.*

class MainPlayerMusicFragment : Fragment() {

    private var listMainMusic: ArrayList<MusicModel> = ArrayList()
    private var mPosition = 0
    private var notification: Notification? = null
    private val mHandler = Handler()
    private var mBounded: Boolean = false
    private var isPlaying = false
    private var playMusicService = PlayMusicService()

    companion object {
        const val DELAY_TIME: Long = 1000
        const val LIST_MUSIC_KEY = "listmusic"
        const val IS_PLAYING_KEY = "isplaying"
        fun newInstance(listMusic: ArrayList<MusicModel>, isPlaying: Boolean) = MainPlayerMusicFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(LIST_MUSIC_KEY, listMusic)
                putBoolean(IS_PLAYING_KEY, isPlaying)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main_player_music, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listMainMusic = it.getParcelableArrayList<MusicModel>(LIST_MUSIC_KEY)!!
            isPlaying = it.getBoolean(IS_PLAYING_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        updateMusic()
        initPlayPauseButton()
    }

    override fun onStart() {
        super.onStart()
        val mIntent = Intent(requireContext(), PlayMusicService::class.java)
        context?.bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBounded) {
            context?.unbindService(mConnection)
            mBounded = false
        }
    }

    private fun initView() {
        imgDiskPlayer.setImageURI(Uri.parse(listMainMusic[mPosition].musicImage))
        tvMusicNamePlayingMain.text = listMainMusic[mPosition].musicName
        tvMusicArtistPlayingMain.text = listMainMusic[mPosition].musicArtist

        if (isReNew) {
            imgRenew.setColorFilter(Color.MAGENTA)
        } else {
            imgRenew.setColorFilter(Color.GRAY)
        }
        if (isShuffle) {
            imgShuffle.setColorFilter(Color.MAGENTA)
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
        }
        val rotation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        imgDiskPlayer.startAnimation(rotation)
        //initPlayPauseButton()
    }

    private fun initPlayPauseButton() {
        if (isPlaying) {
            imgPlayButtonMain.setImageResource(R.drawable.ic_pause)
        } else {
            imgPlayButtonMain.setImageResource(R.drawable.ic_play)
        }
    }

    private fun initListener(){
        imgPlayButtonMain.setOnClickListener {
            initPlayPauseMedia()
        }
        imgNextMain.setOnClickListener {
            nextMusic()
        }
        imgPreviousMain.setOnClickListener {
            previousMusic()
        }
        imgShuffle.setOnClickListener {
            initShuffle()
        }
        imgRenew.setOnClickListener {
            initRenew()
        }
    }

    private fun nextMusic() {
        isPlaying = true
        mPosition++
        if (mPosition > listMainMusic.size - 1) mPosition = 0
        playMusicService.initNextMusic()
        createNotification(mPosition)
        initView()
    }

    private fun previousMusic() {
        isPlaying = true
        mPosition--
        if (mPosition < 0) mPosition = listMainMusic.size - 1
        playMusicService.initPreviousMusic()
        createNotification(mPosition)
        initView()
    }

    private fun initPlayPauseMedia() {
        isPlaying = if (isPlaying) {
            imgPlayButtonMain.setImageResource(R.drawable.ic_play)
            false
        } else {
            imgPlayButtonMain.setImageResource(R.drawable.ic_pause)
            true
        }
        playMusicService.initPlayPause()
    }

    private fun initShuffle() {
        if (!isShuffle) {
            imgShuffle.setColorFilter(Color.MAGENTA)
            isShuffle = true
            Toast.makeText(requireContext(), getString(R.string.toast_shuffle_enable), Toast.LENGTH_SHORT).show()
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
            isShuffle = false
            Toast.makeText(requireContext(), getString(R.string.toast_shuffle_disable), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRenew() {
        if (!isReNew) {
            imgRenew.setColorFilter(Color.MAGENTA)
            isReNew = true
            Toast.makeText(requireContext(), getString(R.string.toast_replay_enable), Toast.LENGTH_SHORT).show()
        } else {
            imgRenew.setColorFilter(Color.GRAY)
            isReNew = false
            Toast.makeText(requireContext(), getString(R.string.toast_replay_disable), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMusic() {
        sbMusicRealTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTimeStart?.text = Units.convertTimeMusic(sbMusicRealTime.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playMusicService.seekTo(sbMusicRealTime.progress)
            }
        })
        var position = mPosition
        val runnable = object : Runnable {
            override fun run() {
                sbMusicRealTime?.max = listMainMusic[mPosition].musicDuration
                mPosition = playMusicService.initPosition()
                val currentPosition = playMusicService.currentPosition()
                if (currentPosition != null) {
                    sbMusicRealTime?.progress = currentPosition
                    tvTimeStart?.text = Units.convertTimeMusic(sbMusicRealTime.progress)
                    tvTimeEnd?.text = Units.convertTimeMusic(listMainMusic[mPosition].musicDuration)
                }
                if (mPosition > position) {
                    try {
                        position = mPosition
                        initView()
                    } catch (e: NullPointerException) {
                    }
                }
                mHandler.postDelayed(this, DELAY_TIME)
            }
        }
        mHandler.post(runnable)
    }

    private var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            playMusicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBounded = true
            val mLocalBinder = service as PlayMusicService.LocalBinder
            playMusicService = mLocalBinder.getServerInstance
            mPosition = playMusicService.initPosition()
            isPlaying = playMusicService.isPlaying()
            initView()
        }
    }

    private fun createNotification(position: Int) {
        notification = Notification(playMusicService)
        val notification = notification?.createNotification(listMainMusic[position], isPlaying)
        playMusicService.startForeground(1, notification)
        isPlaying = playMusicService.isPlaying()
        isReNew = playMusicService.isRenew()
        isShuffle = playMusicService.isShulle()
    }
}
