package asiantech.internship.summer.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

@Suppress("DEPRECATION")
class StepViewPagerAdapter(fragment: FragmentManager) : FragmentStatePagerAdapter(fragment) {

    companion object {
        private const val COUNT_ITEM = 3
    }

    override fun getItem(position: Int): Fragment = StepViewPagerFragment.newInstance(position)

    override fun getCount() = COUNT_ITEM

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

}
