package asiantech.internship.summer.viewpagerandtablayout

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_viewpager.*

class ViewPagerActivity : AppCompatActivity() {
    private var countPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)

        tvSkip.setOnClickListener {
            if (viewPager.currentItem == 2) {
                val intent = Intent(this, TabLayoutActivity::class.java)
                startActivity(intent)
            } else {
                viewPager.currentItem = countPosition + 1
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                countPosition = position
                if (position == 2) {
                    tvSkip.text = "NEXT"
                } else {
                    tvSkip.text = "SKIP"
                }
            }
        })
    }
}
