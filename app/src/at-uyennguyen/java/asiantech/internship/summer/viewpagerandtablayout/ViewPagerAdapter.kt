package asiantech.internship.summer.viewpagerandtablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import asiantech.internship.summer.viewPagerAndTagLayout.HomeFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    companion object {
        const val COUNT_ITEM = 3
    }

    override fun getItem(position: Int): Fragment = HomeFragment.newInstance(position)

    override fun getCount() = COUNT_ITEM
}
