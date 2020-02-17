package asiantech.internship.summer.service

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song
import kotlinx.android.synthetic.`at-hauha`.activity_music.*

class MusicActivity : AppCompatActivity() {
    private val songList = mutableListOf<Song>()
    private lateinit var adapter: MusicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        initAdapter()
    }

    private fun initAdapter(){
        adapter = MusicAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}