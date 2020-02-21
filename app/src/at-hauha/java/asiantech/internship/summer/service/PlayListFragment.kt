package asiantech.internship.summer.service

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
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
        private const val ARG_POS = "position"
        fun newInstance(position: Int) = PlayListFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POS, position)
            }
        }
    }

    private val songList = ArrayList<Song>()
    private lateinit var adapter: MusicAdapter
    private var position = 0
    private var musicService = PlayMusicService()
    private var playIntent: Intent? = null
    private var musicBound = false
    private var isPlaying = false

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            position = getInt(ARG_POS)
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
        adapter.onItemClicked = { it ->
            startMusic(context, songList, it)
        }
        imgNext.setOnClickListener {
            nextMusic()
        }
        imgPlay.setOnClickListener {
            if (musicService.getPosition() < 0) {
                startMusic(requireContext(), songList, position)
            } else {
                pauseSong()
            }
        }
        cardView.setOnClickListener {
            (activity as? MusicActivity)?.replaceMusicFragment(musicService.getPosition(), songList, isPlaying)
        }
    }

    private fun startMusic(context: Context, songList: ArrayList<Song>, position: Int) {
        imgPlay.isSelected = true
        setMusic(songList[position])
        imgNext.isSelected = true
        isPlaying = true
        context.startService(PlayMusicService.getMusicDataIntent(context, songList, position))
    }

    private fun pauseSong() {
        if (!isPlaying) {
            musicService.playSong()
            imgPlay.isSelected = true
            isPlaying = true
            Log.i("XXX",isPlaying.toString())
        } else {
            musicService.pauseSong()
            isPlaying = false
            imgPlay.isSelected = false
            Log.i("XXX",isPlaying.toString())
        }
    }

    private fun initListener() {
        if(position>0){
            setMusic(songList[position])
            imgNext.isSelected = true
        }
        initAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setMusic(song: Song) {
        tvSongName.text = song.title
        val bitmap = Utils.songArt(Uri.parse(song.path), requireContext())
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        imgSongPlay.startAnimation(rotate)
        imgSongPlay.setImageBitmap(bitmap)
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

    private fun nextMusic() {
        musicService.nextSong()
        setMusic(songList[musicService.getPosition()])
    }
}
