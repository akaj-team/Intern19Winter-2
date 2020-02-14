package asiantech.internship.summer.viewpager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.activity_view_pager_main.*

class StepViewPagerActivity : AppCompatActivity() {

    private var numPosition: Int = 0
    private val adapter = StepViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_main)

        initAdapter()
        initListener()
    }

    private fun initAdapter() {
        vpMainActivity.adapter = adapter
        indicator.setViewPager(vpMainActivity)
        vpMainActivity.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position < 2) {
                    tvSkip.text = getString(R.string.textview_text_next)
                } else {
                    tvSkip.text = getString(R.string.textview_text_skip)
                }
                numPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun initListener() {
        tvSkip.setOnClickListener {
            if (numPosition < 2) {
                vpMainActivity.currentItem = numPosition + 1
            } else {
                val intent = Intent(this, ViewHomeActivity::class.java)
                startActivity(intent)
            }

        }
    }
}
