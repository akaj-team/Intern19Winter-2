package asiantech.internship.summer.viewPager_tabLayout

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomeAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    companion object {
        private const val NUMBER_PAGE = 3
    }

    override fun getItem(position: Int): Fragment = StepFragment.newInstance(position)

    override fun getCount() = NUMBER_PAGE

}
