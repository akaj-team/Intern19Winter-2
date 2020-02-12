package asiantech.internship.summer.viewPager_tabLayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_main.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val adapter = HomeAdapter(supportFragmentManager)
//        viewPager.adapter = adapter
    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.llContainer, fragment, null)
                .addToBackStack(null)
                .commit()
    }
}
