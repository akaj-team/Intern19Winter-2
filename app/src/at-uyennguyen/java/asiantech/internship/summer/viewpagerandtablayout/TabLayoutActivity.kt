package asiantech.internship.summer.viewpagerandtablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_tablayout.*

class TabLayoutActivity : AppCompatActivity() {

    private var listFragment = mutableListOf<ListTagLayout>()
    private var adapterTagLayout = TabLayoutAdapter(supportFragmentManager, listFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)
        initData()
        viewPagerOfTabLayout?.adapter = adapterTagLayout
        tabLayout?.setupWithViewPager(viewPagerOfTabLayout)
    }

    private fun initData() {
        listFragment.add(ListTagLayout(FirstTabLayoutFragment(), "PICTURE 1"))
        listFragment.add(ListTagLayout(SecondTabLayoutFragment(), "PICTURE 2"))
        listFragment.add(ListTagLayout(ThirdTabLayoutFragment(), "PICTURE 3"))
    }
}
