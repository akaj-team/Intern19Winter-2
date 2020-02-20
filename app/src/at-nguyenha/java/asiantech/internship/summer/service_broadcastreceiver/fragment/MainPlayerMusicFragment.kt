package asiantech.internship.summer.service_broadcastreceiver.fragment


import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import asiantech.internship.summer.service_broadcastreceiver.model.Units
import kotlinx.android.synthetic.`at-nguyenha`.activity_main_player_music.*

class MainPlayerMusicFragment : Fragment() {

    private lateinit var mediaPlayer: MediaPlayer
    private var listMainMusic: ArrayList<MusicModel> = arrayListOf()
    private var mPosition = 0
    private var isPlaying = false

    companion object {
        const val POSITION_KEY = "position"
        fun newInstace(mPosition: Int) = MainPlayerMusicFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION_KEY, mPosition)
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
            mPosition = it.getInt(POSITION_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgPlayButtonMain.setOnClickListener {
            val ronate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            imgDiskPlayer.startAnimation(ronate)
            imgPlayButtonMain.setImageResource(R.drawable.ic_play)
        }
        initData()
        initView()
        playMusic(Uri.parse(listMainMusic[mPosition].path))
        initListener()
        updateMusic()
    }

    private fun playMusic(uri: Uri) {
        mediaPlayer = MediaPlayer.create(requireContext(), uri)
        mediaPlayer.start()
        isPlaying = (true)
    }

    private fun initData() {
        listMainMusic.apply {
            addAll(Units.insertData(requireContext()))
        }
    }

    private fun initView() {
        imgDiskPlayer.setImageURI(Uri.parse(listMainMusic[mPosition].musicImage))
        tvMusicNamePlayingMain.text = listMainMusic[mPosition].musicName
        tvMusicArtistPlayingMain.text = listMainMusic[mPosition].musicArtist
    }

    private fun initListener(){
        imgNextMain.setOnClickListener {
            mPosition++
            if (mPosition > listMainMusic.size - 1) mPosition = 0
            mediaPlayer.release()
            playMusic(Uri.parse(listMainMusic[mPosition].path))
            initView()
        }
        imgPreviousMain.setOnClickListener {
            mPosition--
            if (mPosition < listMainMusic.size - 1) mPosition = listMainMusic.size - 1
            mediaPlayer.release()
            playMusic(Uri.parse(listMainMusic[mPosition].path))
            initView()
        }
    }

    private fun updateMusic() {
        sbMusicRealTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i("XXXX", progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.i("XXXX", "onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(sbMusicRealTime.progress)
            }
        })
        val mHandler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                sbMusicRealTime.progress = mediaPlayer.currentPosition
                tvTimeStart.text = Units.convertTimeMusic(mediaPlayer.currentPosition.toLong())
                mHandler.postDelayed(this, 1000)
            }
        }
        mHandler.post(runnable)
    }
}
