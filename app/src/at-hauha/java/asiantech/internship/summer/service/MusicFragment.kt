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
        private const val ARG_ISPLAYING = "isPlaying"
        fun newInstance(mPosition: Int, songList: ArrayList<Song>, isPlaying : Boolean) = MusicFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, mPosition)
                putParcelableArrayList(ARG_LIST, songList)
                putBoolean(ARG_ISPLAYING,isPlaying)
            }
        }
    }

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
            isPlaying = getBoolean(ARG_ISPLAYING)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireContext(), "True", Toast.LENGTH_LONG).show()
        mHandler.removeCallbacks(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mHandler.removeCallbacks(runnable)
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
            setMusic(songList[position])
        }
        tvBack.setOnClickListener {
            (activity as? MusicActivity)?.replacePlayListPragment(musicService.getPosition())
        }
        imgShuffle.setOnClickListener {
            shuffleMusic()
        }
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
    }

    private fun prevMusic() {
        musicService.prevSong()
    }

    private fun pauseSong() {
        if (isPlaying) {
            musicService.playSong()
            isPlaying = false
            imgPlay.isSelected = false
        } else {
            musicService.pauseSong()
            imgPlay.isSelected = true
            isPlaying = true
        }
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
                val currentPosition = musicService?.currentPosition()
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