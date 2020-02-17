package asiantech.internship.summer.viewpagerandtablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabLayoutAdapter(fragmentManager: FragmentManager, private var listFragment: MutableList<ListTagLayout>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return listFragment[position].listFragment
    }

    override fun getCount() = listFragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return listFragment[position].title
    }
}
