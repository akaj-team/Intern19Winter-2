package asiantech.internship.summer.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R

class LayoutMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_main)
        replaceFragment(EditProfileFragment.newInstance("Nick Evans", "asiantech@gmail.com", ""))

    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, fragment, null)
                .addToBackStack(null)
                .commit()
    }
}
