package asiantech.internship.summer.service

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song
import kotlinx.android.synthetic.`at-hauha`.fragment_music.*

class MusicFragment : Fragment() {

    companion object {
        private const val DELAY_TIME: Long = 500
        private const val ARG_POSITION = "position"
        fun newInstance(mPosition: Int) = MusicFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, mPosition)
            }
        }
    }

    private var position = 0
    private var isPlaying = false
    private val songList = mutableListOf<Song>()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var tvStart: TextView
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_music, container, false)
        tvStart = view.findViewById(R.id.tvStart)
        seekBar = view.findViewById(R.id.seekBar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        val song = songList[position]
        setMusic(song)
        if (isPlaying) {
            mediaPlayer.release()
        }
        startMusic(Uri.parse(song.path))
        pauseMusic()
        nextMusic()
        previousMusic()
        isPlaying = true
        updateMusic()
    }

    private fun setMusic(song: Song) {
        val bitmap = Utils.songArt(Uri.parse(song.path), requireContext())
        imgSong.setImageBitmap(bitmap)
        rotateImage()
        tvSongName.text = song.title
        tvDuration.text = Utils.convertTime(song.duration)
        seekBar.max = (song.duration)
    }

    private fun pauseMusic() {
        imgPlay.setOnClickListener {
            if (!isPlaying) {
                mediaPlayer.start()
                isPlaying = true
                imgPlay.isSelected = false
            } else {
                mediaPlayer.pause()
                isPlaying = false
                imgPlay.isSelected = true
            }
        }
    }

    private fun nextMusic() {
        imgNext.setOnClickListener {
            position++
            if (position > songList.size - 1) run {
                position = 0
            }
            Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
            mediaPlayer.release()
            startMusic(Uri.parse(songList[position].path))
            setMusic(songList[position])
        }
    }

    private fun previousMusic() {
        imgPrevious.setOnClickListener {
            position--
            if (position < 0) run {
                position = songList.size - 1
            }
            Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
            mediaPlayer.release()
            startMusic(Uri.parse(songList[position].path))
            setMusic(songList[position])

        }
    }

    private fun rotateImage() {
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        imgSong.startAnimation(rotate)
    }

    private fun updateMusic() {

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(this@MusicFragment.seekBar.progress)
            }
        })
        val mHandler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                seekBar.progress = mediaPlayer.currentPosition
                tvStart.text = Utils.convertTime(mediaPlayer.currentPosition)
                if (mediaPlayer.currentPosition < mediaPlayer.duration) {
                    mHandler.postDelayed(this, DELAY_TIME)
                }
            }
        }
        mHandler.post(runnable)
    }

    private fun startMusic(uri: Uri) {
        mediaPlayer = MediaPlayer.create(requireContext(), uri)
        mediaPlayer.start()
    }

    private fun initData() {
        songList.clear()
        songList.addAll(Utils.getSongDevices(requireContext()))
    }


}

