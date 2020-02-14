package asiantech.internship.summer.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

@Suppress("DEPRECATION")
class ViewHomeAdapter(fragment: FragmentManager) : FragmentStatePagerAdapter(fragment) {

    companion object {
        private const val COUNT_ITEM = 3
    }

    override fun getItem(position: Int): Fragment = ViewHomeFragment.newInstance(position)

    override fun getCount(): Int = COUNT_ITEM

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "HOME"
            2 -> "INFO"
            else -> return "ANOTHER"
        }
    }
}
