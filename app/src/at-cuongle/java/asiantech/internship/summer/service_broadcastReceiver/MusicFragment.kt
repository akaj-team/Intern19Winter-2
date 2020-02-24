package asiantech.internship.summer.service_broadcastReceiver

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_list_music.*

class MusicFragment : Fragment(), View.OnClickListener {
    companion object {
        private const val PERMISSION_CODE = 101
        private const val DEFAULT_VALUE = 0
    }

    private lateinit var receiver: BroadcastReceiver
    private var positionMusicPlaying = DEFAULT_VALUE
    private var musicService = ForegroundService()
    private var musicBound = false
    private var isPlaying = false
    private val music = mutableListOf<Music>()
    private lateinit var musicAdapter: MusicAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        initAdapter()
        initData()
        initListeners()
    }

    private fun createIntentFilter() {
        val filter = IntentFilter()
        filter.apply {
            addAction(MusicAction().PRIVIOUS)
            addAction(MusicAction().PAUSE)
            addAction(MusicAction().NEXT)
        }
        requireContext().registerReceiver(receiver, filter)
    }

    override fun onResume() {
        super.onResume()
        receiver = object : MusicReceiver(ForegroundService()) {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.extras != null) {
                    val action = intent.action
                    Log.i("XXX", "Music Fragment action: $action")
                    when (intent?.action) {
                        MusicAction().NEXT -> {
                            positionMusicPlaying++
                            setStatus()
                        }
                    }
                }
            }
        }
        createIntentFilter()
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(context, ForegroundService::class.java)
        context?.bindService(intent, musicConnection, Context.BIND_AUTO_CREATE)
        positionMusicPlaying = musicService.getPosition()
        setStatus()
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

    override fun onClick(view: View?) {
        when (view) {
            btnNext -> {
                sendAction(MusicAction().NEXT)
                positionMusicPlaying++
                setStatus()
            }
            btnPlayPause -> onPausePlayMusic()
        }
    }

    private fun playMusic(position: Int) {
        ForegroundService.startService(requireContext(), position)
        setStatus()
        btnPlayPause.isSelected = true
        isPlaying = true
    }

    private fun setStatus() {
        imgMusicBottom.setImageURI(music[positionMusicPlaying].image)
        tvNameMusicBottom.text = music[positionMusicPlaying].name
        tvArtistBottom.text = music[positionMusicPlaying].artist
    }

    private fun onPausePlayMusic() {
        btnPlayPause.setOnClickListener {
            isPlaying = if (!isPlaying) {
                sendAction(MusicAction().PLAY)
                btnPlayPause.isSelected = true
                true
            } else {
                btnPlayPause.isSelected = false
                sendAction(MusicAction().PAUSE)
                false
            }
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let { context?.let { context -> ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) } }
                    == PackageManager.PERMISSION_DENIED ||
                    this.let {
                        context?.let { context ->
                            ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        }
                    }
                    == PackageManager.PERMISSION_DENIED) {
                val permission =
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            }
        }
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter(music)
        rvMusic.adapter = musicAdapter
    }

    private fun initData() {
        music.clear()
        music.addAll(MusicData.getMusic(requireContext()))
    }

    private fun initListeners() {
        musicAdapter.onSongClicked = {
            Toast.makeText(context, music[it].name, Toast.LENGTH_SHORT).show()
            positionMusicPlaying = it
            playMusic(it)
        }
        cardViewBottom.setOnLongClickListener {
            (activity as? ServiceBroadCastActivity)?.replaceFragment(MainScreenMusicFragment.newInstance(positionMusicPlaying))
            true
        }
        btnPlayPause.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    private fun sendAction(action: String) {
        val intent = Intent(requireContext(), ForegroundService::class.java)
        intent.action = action
        intent.putExtra(action, "1")
        context?.startService(intent)
    }
}
