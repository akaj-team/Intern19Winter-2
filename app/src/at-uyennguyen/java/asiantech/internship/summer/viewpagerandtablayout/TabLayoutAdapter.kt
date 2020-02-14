package asiantech.internship.summer.viewpagerandtablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabLayoutAdapter(fragmentManager: FragmentManager, private var listFragment: MutableList<ListTagLayoutFragment>) : FragmentStatePagerAdapter(fragmentManager) {

    private var listTitle = mutableListOf("PICTURE 1", "PICTURE 2", "PICTURE 3")

    override fun getItem(position: Int): Fragment {
        return listFragment[position].listFragment
    }

    override fun getCount() = listFragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }
}
