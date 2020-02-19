package asiantech.internship.summer.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song
import kotlinx.android.synthetic.`at-hauha`.fragment_playlist.*

class PlayListFragment : Fragment() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private var position: Int = -1
    private var isPlaying: Boolean = false
    private val songList = mutableListOf<Song>()
    private lateinit var adapter: MusicAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), getString(R.string.permision_granted), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        initListener()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            initData()
        }
    }

    private fun initData() {
        songList.clear()
        songList.addAll(Utils.getSongDevices(requireContext()))
    }

    private fun initAdapter(context: Context) {
        adapter = MusicAdapter(songList, context)
        adapter.onItemClicked = {
            position = it + 1
            imgNext.isSelected = true
            if (position > songList.size - 1) {
                position = -1
            }
            val uriSong = Uri.parse(songList[it].path)
            setMusic(songList[it])
            if (isPlaying) {
                mediaPlayer.release()
            }
            startMusic(uriSong)
            isPlaying = true
            pauseMusic()
            nextMusic(position)
        }
        cardView.setOnClickListener {
            if (position > -1) {
                mediaPlayer.release()
                (activity as? MusicActivity)?.replaceMusicFragment(position - 1)
            }
        }
    }

    private fun initListener() {
        initAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
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

    private fun nextMusic(position: Int) {
        var mPosition = position + 1
        imgNext.setOnClickListener {
            if(mPosition > 0) {
                mediaPlayer.release()
                startMusic(Uri.parse(songList[mPosition].path))
                setMusic(songList[mPosition])
                mPosition++
            }
        }
    }

    private fun startMusic(uri: Uri) {
        mediaPlayer = MediaPlayer.create(requireContext(), uri)
        mediaPlayer.start()
    }

    private fun setMusic(song: Song) {
        tvSongName.text = song.title
        val bitmap = Utils.songArt(Uri.parse(song.path), requireContext())
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        imgSongPlay.startAnimation(rotate)
        imgSongPlay.setImageBitmap(bitmap)
    }
}