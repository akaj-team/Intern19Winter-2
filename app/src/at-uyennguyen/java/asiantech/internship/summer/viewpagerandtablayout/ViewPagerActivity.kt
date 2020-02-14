package asiantech.internship.summer.viewpagerandtablayout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_viewpager.*

class ViewPagerActivity : AppCompatActivity() {
    companion object{
        private const val LAST_POSITION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
        tvSkip.text = getString(R.string.skip)

        tvSkip.setOnClickListener {
            if (viewPager.currentItem == LAST_POSITION) {
                val intent = Intent(this, TabLayoutActivity::class.java)
                startActivity(intent)
            } else {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == LAST_POSITION) {
                    tvSkip.text = getString(R.string.next)
                } else {
                    tvSkip.text = getString(R.string.skip)
                }
            }
        })
    }
}
