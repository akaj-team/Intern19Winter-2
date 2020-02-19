package asiantech.internship.summer.service

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class MusicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        supportFragmentManager.beginTransaction()
                .replace(R.id.action_container,PlayListFragment(),null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceMusicFragment(mPosition: Int) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, MusicFragment.newInstance(mPosition), null)
                .addToBackStack(null)
                .commit()
    }
}