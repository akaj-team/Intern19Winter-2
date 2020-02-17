package asiantech.internship.summer.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-daiho`.activity_tab_layout.*
import kotlinx.android.synthetic.`at-daiho`.activity_tutorial.viewPager

class TabLayoutActivity : AppCompatActivity() {

    private var listFragments = mutableListOf<HomeFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)
        initFragmentDatas()
        setupViewPagerAdapter()
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initFragmentDatas() {
        listFragments.add(HomeFragment.newInstance(0))
        listFragments.add(HomeFragment.newInstance(1))
        listFragments.add(HomeFragment.newInstance(2))
    }

    private fun setupViewPagerAdapter() {
        val adapter = TabLayoutAdapter(supportFragmentManager, listFragments)
        viewPager.adapter = adapter
    }
}
