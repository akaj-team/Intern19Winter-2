package asiantech.internship.summer.viewPager_tabLayout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_home.*

class ViewPagerActivity : AppCompatActivity() {
    private var currentPosition = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
        tvAction.text = getString(R.string.tv_skip)
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
                    1 -> tvAction.text = getString(R.string.tv_skip)
                    2 -> tvAction.text = getString(R.string.tv_next)
                }
                currentPosition = position + 1
            }
        })
        tvAction.setOnClickListener {
            if (currentPosition < 3) {
                viewPager.currentItem = currentPosition
            } else {
                val intent = Intent(this, TabLayoutActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
