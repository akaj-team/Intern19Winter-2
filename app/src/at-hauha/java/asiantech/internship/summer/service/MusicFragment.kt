package asiantech.internship.summer.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
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
import asiantech.internship.summer.service.model.Song
import kotlinx.android.synthetic.`at-hauha`.fragment_music.*

class MusicFragment : Fragment() {

    companion object {
        private const val DELAY_TIME: Long = 500
        private const val ARG_POSITION = "position"
        private const val ARG_LIST = "songList"
        fun newInstance(mPosition: Int, songList: ArrayList<Song>) = MusicFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, mPosition)
                putParcelableArrayList(ARG_LIST, songList)
            }
        }
    }

    private var position = 0
    private var musicService = PlayMusicService()
    private var playIntent: Intent? = null
    private var musicBound = false
    private lateinit var songList: ArrayList<Song>
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            position = getInt(ARG_POSITION)
            songList = getParcelableArrayList<Song>(ARG_LIST) as ArrayList<Song>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_music, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_LONG).show()
        val song = songList[position]
        setMusic(song)
        updateMusic()
        imgPlay.setOnClickListener {
            pauseSong()
        }
        imgNext.setOnClickListener {
            nextMusic()
            setMusic(songList[musicService.getPosition()])
        }
        imgPrevious.setOnClickListener {
            prevMusic()
            setMusic(songList[musicService.getPosition()])
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

    private fun setMusic(song: Song) {
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
    }

    private fun prevMusic(){
        musicService.prevSong()
    }

    private fun pauseSong() {
        if (!isPlaying) {
            musicService.playSong()
            isPlaying = true
            imgPlay.isSelected = false
        } else {
            musicService.pauseSong()
            imgPlay.isSelected = true
            isPlaying = false
        }
    }

    private fun updateMusic() {

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        val mHandler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                val currentPosition = musicService?.currentPosition()
                if (currentPosition != null) {
                    seekBar.progress = currentPosition
                    tvStart.text = Utils.convertTime(currentPosition)
                }
                mHandler.postDelayed(this, DELAY_TIME)
            }
        }
        mHandler.post(runnable)
    }
}