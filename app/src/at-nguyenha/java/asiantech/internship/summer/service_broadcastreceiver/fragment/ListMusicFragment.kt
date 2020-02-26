package asiantech.internship.summer.service_broadcastreceiver.fragment


import android.Manifest
import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.MyMainActivity
import asiantech.internship.summer.service_broadcastreceiver.Notification
import asiantech.internship.summer.service_broadcastreceiver.adapter.MusicAdapter
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import asiantech.internship.summer.service_broadcastreceiver.model.Units
import asiantech.internship.summer.service_broadcastreceiver.service.PlayMusicService
import asiantech.internship.summer.service_broadcastreceiver.service.PlayMusicService.LocalBinder
import kotlinx.android.synthetic.`at-nguyenha`.fragment_list_music.*

class ListMusicFragment : Fragment() {

    private var listMusic: ArrayList<MusicModel> = arrayListOf()
    private var listPath: ArrayList<String> = ArrayList()
    private var adapter = MusicAdapter(listMusic)
    private var mPosition: Int = 0
    private var notification: Notification? = null
    private var mBounded: Boolean = false
    private var isPlaying = false
    private var playMusicService = PlayMusicService()

    companion object {
        private const val REQUEST_CODE = 1000
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_music, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAdapter()
        initListener()
        //initPlayPauseButton()
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
        } else initData()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Successes", Toast.LENGTH_LONG).show()
                initData()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val mIntent = Intent(requireContext(), PlayMusicService::class.java)
        context?.bindService(mIntent, mConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBounded) {
            context?.unbindService(mConnection)
            mBounded = false
        }
    }

    private fun initBottomView() {
        imgPlaying.setImageURI(Uri.parse(listMusic[mPosition].musicImage))
        tvPlaying.text = listMusic[mPosition].musicName
        initPlayPauseButton()
    }

    private fun initView() {
        recyclerMusicActivity.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initData() {
        listMusic.clear()
        listMusic.apply {
            addAll(Units.insertData(requireContext()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initAdapter() {
        recyclerMusicActivity.adapter = adapter
        adapter.onItemClicked = {
            mPosition = it
            Toast.makeText(requireContext(), "Playing " + listMusic[it].musicName, Toast.LENGTH_SHORT).show()
            val musicDataIntent = Intent(requireContext(), PlayMusicService::class.java)
            musicDataIntent.putStringArrayListExtra(MusicAdapter.MUSIC_LIST, listPath)
            musicDataIntent.putParcelableArrayListExtra(MusicAdapter.MUSIC_LIST, listMusic)
            musicDataIntent.putExtra(MusicAdapter.MUSIC_ITEM_POSSITION, mPosition)
            requireContext().startForegroundService(musicDataIntent)
            isPlaying = true
            initBottomView()
        }
    }

    private fun initListener() {
        imgPlayPause.setOnClickListener {
            initPlayPauseMedia()
        }
        imgNext.setOnClickListener {
            nextMusic()
        }
        imgPrevious.setOnClickListener {
            previousMusic()
        }
        cardViewPlayMusic.setOnClickListener {
            initMainMediaPage()
        }
    }

    private fun initMainMediaPage() {
        (activity as MyMainActivity)
                .replaceFragment(MainPlayerMusicFragment
                        .newInstance(listMusic, isPlaying)
                        , true)
    }

    private fun initPlayPauseButton() {
        if (isPlaying) {
            imgPlayPause.setImageResource(R.drawable.ic_pause)
        } else {
            imgPlayPause.setImageResource(R.drawable.ic_play)
        }
    }

    private fun initPlayPauseMedia() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlayPause.setImageResource(R.drawable.ic_play)
                false
            }
            else -> {
                imgPlayPause.setImageResource(R.drawable.ic_pause)
                true
            }
        }
        playMusicService.initPlayPause()
    }

    private fun nextMusic() {
        //isPlaying = false
        playMusicService.initNextMusic()
        mPosition = playMusicService.initPosition()
        createNotification(mPosition)
        initBottomView()
    }

    private fun previousMusic() {
        //isPlaying = false
        playMusicService.initPreviousMusic()
        mPosition = playMusicService.initPosition()
        createNotification(mPosition)
        initBottomView()
    }

    private var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            playMusicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBounded = true
            val mLocalBinder = service as LocalBinder
            playMusicService = mLocalBinder.getServerInstance
            mPosition = playMusicService.initPosition()
            isPlaying = playMusicService.isPlaying()
            initBottomView()
        }
    }

    private fun createNotification(position: Int) {
        notification = Notification(playMusicService)
        val notification = notification?.createNotification(listMusic[position], isPlaying)
        playMusicService.startForeground(1, notification)
        isPlaying = playMusicService.isPlaying()
    }
}
