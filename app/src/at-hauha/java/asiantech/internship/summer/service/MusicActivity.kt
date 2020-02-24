package asiantech.internship.summer.service

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song

class MusicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        replacePlayListFragment(0,false)
    }

    fun replacePlayListFragment(position: Int, isPlaying: Boolean) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, PlayListFragment.newInstance(position, isPlaying).apply {
                }, null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceMusicFragment(position: Int, songList: ArrayList<Song>, isPlaying: Boolean) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, MusicFragment.newInstance(position, songList, isPlaying), null)
                .addToBackStack(null)
                .commit()
    }
}
