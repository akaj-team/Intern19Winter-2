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
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    1 -> tvSkip.text = getString(R.string.texview_skip)
                    2 -> tvSkip.text = getString(R.string.texview_next)
                }
                pagePosition = position + 1
            }
        })
        tvSkip.setOnClickListener {
            if (pagePosition < 3) {
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
