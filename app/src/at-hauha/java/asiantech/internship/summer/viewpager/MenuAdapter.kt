package asiantech.internship.summer.viewpager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MenuAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titleList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int) = BlankFragment.newInstance(position)

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addTitle(title: String) {
        titleList.add(title)
    }
}
