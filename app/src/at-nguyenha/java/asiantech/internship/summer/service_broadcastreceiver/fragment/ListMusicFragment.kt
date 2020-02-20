package asiantech.internship.summer.service_broadcastreceiver.fragment


import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.MyMainActivity
import asiantech.internship.summer.service_broadcastreceiver.adapter.MusicAdapter
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import asiantech.internship.summer.service_broadcastreceiver.model.Units
import kotlinx.android.synthetic.`at-nguyenha`.fragment_list_music.*

class ListMusicFragment : Fragment() {

    private var listMusic: ArrayList<MusicModel> = arrayListOf()
    private var adapter = MusicAdapter(listMusic)
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    private var position: Int = 0

    companion object {
        private const val REQUEST_CODE = 1000
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_music, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
        initPlayPause()

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
        } else initData()

    }

    private fun initView() {
        recyclerMusicActivity.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun initData() {
        listMusic.apply {
            addAll(Units.insertData(requireContext()))
        }
    }

    private fun initAdapter() {
        recyclerMusicActivity.adapter = adapter
        adapter.onItemClicked = {
            position = it
            val uriSong = listMusic[it].path
            if (!isPlaying) {
                playMusic(Uri.parse(uriSong))
                initBottomView(Uri.parse(listMusic[it].musicImage))
                isPlaying = true
            } else {
                mediaPlayer.release()
                playMusic(Uri.parse(uriSong))
                initBottomView(Uri.parse(listMusic[it].musicImage))
            }
        }
        imgNext.setOnClickListener {
            position++
            if (position > listMusic.size - 1) position = 0
            mediaPlayer.release()
            playMusic(Uri.parse(listMusic[position].path))
            initBottomView(Uri.parse(listMusic[position].musicImage))
        }
        imgPrevious.setOnClickListener {
            position--
            if (position < listMusic.size - 1) position = listMusic.size - 1
            mediaPlayer.release()
            playMusic(Uri.parse(listMusic[position].path))
            initBottomView(Uri.parse(listMusic[position].musicImage))
        }
        cardViewPlayMusic.setOnClickListener {
            (activity as MyMainActivity).replaceFragment(MainPlayerMusicFragment.newInstace(position), true)
        }
    }

    private fun initBottomView(uriArt: Uri) {
        imgPlaying.setImageURI(uriArt)
        tvPlaying.text = listMusic[position].musicName
    }

    private fun playMusic(uri: Uri) {
        mediaPlayer = MediaPlayer.create(requireContext(), uri)
        mediaPlayer.start()
        isPlaying = (true)
    }

    private fun initPlayPause() {
        imgPlayButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                imgPlayButton.setImageResource(R.drawable.ic_play)
            } else {
                imgPlayButton.setImageResource(R.drawable.ic_pause)
                mediaPlayer.start()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Successes", Toast.LENGTH_LONG).show()
                initData()
            }
        }
    }
}
