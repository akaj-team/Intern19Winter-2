package asiantech.internship.summer.viewPager_tabLayout

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_tab_layout.*

class TabLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)
        initVIew()
    }

    private fun initVIew() {
        val adapter = TabLayoutAdapter(supportFragmentManager)
        adapter.apply {
            addFragment(HomeFragment(), "HOME")
            addFragment(InformationFragment(), "INFO")
            addFragment(AnotherFragment(), "ANOTHER")
        }
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setBackgroundColor(Color.YELLOW)
    }
}
