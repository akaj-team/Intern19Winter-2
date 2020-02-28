package asiantech.internship.summer.savedata.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import asiantech.internship.summer.savedata.fragment.DoneFragment
import asiantech.internship.summer.savedata.fragment.ToDoFragment

@Suppress("DEPRECATION")
class ToDoAdapter(fragment: FragmentManager) : FragmentStatePagerAdapter(fragment) {

    companion object {
        private const val COUNT_ITEM = 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ToDoFragment()
            else -> DoneFragment()
        }
    }

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

    override fun getCount() = COUNT_ITEM

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "TO DO"
            else -> "DONE"
        }
    }
}
