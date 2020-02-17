package asiantech.internship.summer.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabLayoutAdapter(frmManager: FragmentManager, private var listFragments: MutableList<HomeFragment>) : FragmentStatePagerAdapter(frmManager) {

    private var listTitles = mutableListOf("Tab 1", "Tab 2","Tab 3")

    override fun getItem(position: Int): Fragment {
        return listFragments[position]
    }

    override fun getCount() = listFragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitles[position]
    }
}
