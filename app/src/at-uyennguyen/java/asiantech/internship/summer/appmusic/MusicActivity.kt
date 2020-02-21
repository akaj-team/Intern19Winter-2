package asiantech.internship.summer.appmusic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        val listMusicFragment = ListMusicFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, listMusicFragment)
                .commit()
    }
}
