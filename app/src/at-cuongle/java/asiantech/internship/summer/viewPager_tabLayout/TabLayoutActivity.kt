package asiantech.internship.summer.viewPager_tabLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_tab_layout.*

class TabLayoutctivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)
        initVIew()
    }
    private fun initVIew() {
        val adapter = TabLayoutAdapter(supportFragmentManager)
        adapter.apply {
            addFragment(InformationFragment())
            addFragment(InformationFragment())
            addFragment(InformationFragment())
        }
        viewPager2.adapter = adapter
    }
}
