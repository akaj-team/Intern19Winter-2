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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
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
    private var boundService = PlayMusicService()

    companion object {
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
        initButtonPlay()
    }

    private fun initButtonPlay() {
        if (isPlaying) {
            imgPlayButtonMain.setImageResource(R.drawable.ic_pause)
        } else {
            imgPlayButtonMain.setImageResource(R.drawable.ic_play)
        }
    }

    private fun initListener(){
        imgPlayButtonMain.setOnClickListener {
            isPlaying = if (isPlaying) {
                imgPlayButtonMain.setImageResource(R.drawable.ic_play)
                false
            } else {
                imgPlayButtonMain.setImageResource(R.drawable.ic_pause)
                true
            }
            boundService.initPlayPause()
        }
        imgNextMain.setOnClickListener {
            mPosition++
            if (mPosition > listMainMusic.size - 1) mPosition = 0
            boundService.initNextMusic()
            createNotification(mPosition)
            initView()
        }
        imgPreviousMain.setOnClickListener {
            mPosition--
            if (mPosition < 0) mPosition = listMainMusic.size - 1
            boundService.initPreviousMusic()
            initView()
        }
        imgShuffle.setOnClickListener {
            if (!isShuffle) {
                imgShuffle.setColorFilter(Color.MAGENTA)
                isShuffle = true
            } else {
                imgShuffle.setColorFilter(Color.GRAY)
                isShuffle = false
            }
        }
        imgRenew.setOnClickListener {
            if (!isReNew) {
                imgRenew.setColorFilter(Color.MAGENTA)
                isReNew = true
            } else {
                imgRenew.setColorFilter(Color.GRAY)
                isReNew = false
            }
            Log.i("XXX", boundService.isRenew().toString())
        }
    }

    private fun updateMusic() {
        sbMusicRealTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                boundService.seekTo(sbMusicRealTime.progress)
            }
        })
        val runnable = object : Runnable {
            override fun run() {
                val position = mPosition
                sbMusicRealTime?.max = listMainMusic[mPosition].musicDuration
                val currentPosition = boundService.currentPosition()
                if (currentPosition != null) {
                    sbMusicRealTime?.progress = currentPosition
                    tvTimeStart?.text = Units.convertTimeMusic(sbMusicRealTime.progress)
                    tvTimeEnd?.text = Units.convertTimeMusic(listMainMusic[mPosition].musicDuration)
                }
                mPosition = boundService.initPosition()
                if (mPosition > position) {
                    initView()
                }
                mHandler.postDelayed(this, 1000)
            }
        }
        mHandler.post(runnable)
    }

    private var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            boundService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBounded = true
            val mLocalBinder = service as PlayMusicService.LocalBinder
            boundService = mLocalBinder.getServerInstance
            mPosition = boundService.initPosition()
            isPlaying = boundService.isPlaying()
            initView()
        }
    }

    private fun createNotification(position: Int) {
        notification = Notification(boundService)
        val notification = notification?.createNotification(listMainMusic[position], isPlaying)
        boundService.startForeground(1, notification)
        isPlaying = boundService.isPlaying()
        isReNew = boundService.isRenew()
    }
}
