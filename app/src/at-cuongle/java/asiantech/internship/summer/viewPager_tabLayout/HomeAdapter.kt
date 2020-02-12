package asiantech.internship.summer.viewPager_tabLayout

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class HomeAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val NUMBER_PAGE = 3
    }

    override fun getItem(position: Int): Fragment {
        return HomeFragment.newInstance(position)
    }

    override fun getCount() = NUMBER_PAGE
    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE
}
