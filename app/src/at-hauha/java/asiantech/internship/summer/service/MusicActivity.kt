package asiantech.internship.summer.service

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song

class MusicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, PlayListFragment(), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceMusicFragment(position: Int, songList: ArrayList<Song>) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, MusicFragment.newInstance(position,songList), null)
                .addToBackStack(null)
                .commit()
    }
}