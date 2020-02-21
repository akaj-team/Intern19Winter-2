package asiantech.internship.summer.service_broadcastreceiver.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import asiantech.internship.summer.service_broadcastreceiver.service.PlayMusicService
import kotlinx.android.synthetic.`at-nguyenha`.activity_main_player_music.*

class MainPlayerMusicFragment : Fragment() {

    private lateinit var mMediaPlayer: MediaPlayer
    private var listMainMusic: ArrayList<MusicModel> = ArrayList()
    private var mPosition = 0
    var mBounded: Boolean = false
    var boundService: PlayMusicService? = null

    companion object {
        const val POSITION_KEY = "position"
        const val LIST_MUSIC_KEY = "listmusic"
        fun newInstace(listMusic : ArrayList<MusicModel>, position : Int) = MainPlayerMusicFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(LIST_MUSIC_KEY, listMusic)
                putInt(POSITION_KEY, position)
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
            mPosition = it.getInt(POSITION_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        updateMusic()
    }

    private fun initView() {
        //mPosition = boundService?.initPosition()!!
        imgDiskPlayer.setImageURI(Uri.parse(listMainMusic[mPosition].musicImage))
        tvMusicNamePlayingMain.text = listMainMusic[mPosition].musicName
        tvMusicArtistPlayingMain.text = listMainMusic[mPosition].musicArtist
    }

    private fun initListener(){
        imgPlayButtonMain.setOnClickListener {
            boundService?.initPlayPause()
        }
        imgNextMain.setOnClickListener {
            mPosition++
            if (mPosition > listMainMusic.size - 1) mPosition = 0
            boundService?.initNextMusic()
            initView()
        }
        imgPreviousMain.setOnClickListener {
            mPosition--
            if (mPosition < listMainMusic.size - 1) mPosition = listMainMusic.size - 1
            boundService?.initPreviousMusic()
            initView()
        }
    }

    private fun updateMusic() {
        sbMusicRealTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mMediaPlayer.seekTo(sbMusicRealTime.progress)
            }
        })
        val mHandler = Handler()
        val runnable = object : Runnable {
            override fun run() {
//                sbMusicRealTime.progress = mMediaPlayer.currentPosition
//                tvTimeStart.text = Units.convertTimeMusic(mMediaPlayer.currentPosition.toLong())
                mHandler.postDelayed(this, 1000)
            }
        }
        mHandler.post(runnable)
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

    private var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            boundService = null
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBounded = true
            val mLocalBinder = service as PlayMusicService.LocalBinder
            boundService = mLocalBinder.getServerInstance
        }
    }
}
