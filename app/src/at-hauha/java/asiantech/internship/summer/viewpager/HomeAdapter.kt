package asiantech.internship.summer.viewpager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class HomeAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val NUMBER_VIEWPAGER = 3
    }

    override fun getItem(position: Int) = HomeFragment.newInstance(position)

    override fun getCount() = NUMBER_VIEWPAGER

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

}
