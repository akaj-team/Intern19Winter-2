package asiantech.internship.summer.viewpager

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class HomeAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val NUMBER_VIEWPAGER = 3
    }

    override fun getItem(position: Int) = HomeFragment.newInstance(position)

    override fun getCount() = NUMBER_VIEWPAGER + 2

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val modelPosition = mapPagerPositionToModelPosition(position)
        HomeFragment.newInstance(modelPosition)
        return super.instantiateItem(container, position)
    }

    fun getRealCount() = NUMBER_VIEWPAGER

    private fun mapPagerPositionToModelPosition(pagerPosition: Int): Int {
        if (pagerPosition == 0) {
            return getRealCount() - 1
        }
        if (pagerPosition == getRealCount() + 1) {
            return 0
        }
        return pagerPosition - 1
    }
}
