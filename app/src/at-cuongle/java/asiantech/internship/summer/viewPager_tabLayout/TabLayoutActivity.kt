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
        initView()
    }

    private fun initView() {
        val adapter = TabLayoutAdapter(supportFragmentManager)
        adapter.apply {
            addFragment(HomeFragment(), getString(R.string.tv_tablayout_home))
            addFragment(InformationFragment(), getString(R.string.tv_tablayout_infor))
            addFragment(AnotherFragment(), getString(R.string.tv_tablayout_another))
        }
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setBackgroundColor(Color.YELLOW)
    }
}
