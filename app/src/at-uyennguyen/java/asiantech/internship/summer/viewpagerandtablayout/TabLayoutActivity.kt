package asiantech.internship.summer.viewpagerandtablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_taglayout.*

class TabLayoutActivity : AppCompatActivity() {
    private var listFragment = mutableListOf<ListTagLayoutFragment>()
    private var adapterTagLayout = TabLayoutAdapter(supportFragmentManager, listFragment)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_taglayout)
        initData()
        viewPagerOfTabLayout?.adapter = adapterTagLayout
        tabLayout?.setupWithViewPager(viewPagerOfTabLayout)
    }

    private fun initData() {
        listFragment.add(ListTagLayoutFragment(FirstFragment()))
        listFragment.add(ListTagLayoutFragment(SecondFragment()))
        listFragment.add(ListTagLayoutFragment(ThirdFragment()))
        listFragment.shuffle()
    }
}
