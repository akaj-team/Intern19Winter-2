package asiantech.internship.summer.service_broadcastreceiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.fragment.ListMusicFragment

class MyMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_song)
        replaceFragment(ListMusicFragment())
    }

    internal fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flListMusicActivity, fragment, null)
        if (isAddToBackStack) {
            ft.addToBackStack(null)
        }
        ft.commit()
    }
}
