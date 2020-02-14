package asiantech.internship.summer.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var pagePosition = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val adapter = HomeAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
        viewPager.setCurrentItem(1, false)
        
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            private var jumpPosition = -1

            override fun onPageScrollStateChanged(state: Int) {
                if (jumpPosition >= 0 && state == ViewPager.SCROLL_STATE_IDLE) {
                    viewPager.setCurrentItem(jumpPosition, false)
                    jumpPosition = -1
                }
            }

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> jumpPosition = adapter.getRealCount()
                    1 -> tvSkip.text = getString(R.string.texview_skip)
                    3 -> tvSkip.text = getString(R.string.texview_next)
                    4 -> jumpPosition = 1
                }
                pagePosition = position + 1
            }
        })
        tvSkip.setOnClickListener {
            if (pagePosition < 4) {
                viewPager.currentItem = pagePosition
            } else {
                replaceFragment()
            }
        }

    }

    private fun replaceFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, MenuFragment(), null)
                .addToBackStack(null)
                .commit()
    }

}
