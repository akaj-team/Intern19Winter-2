package asiantech.internship.summer.viewPager_tabLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    companion object {
        private const val NUMBER_PAGE = 3
    }

    override fun getItem(position: Int): Fragment = ViewPagerFragment.newInstance(position)
    override fun getCount() = NUMBER_PAGE
}
