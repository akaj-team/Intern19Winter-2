package asiantech.internship.summer.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-daiho`.activity_tutorial.*
import kotlinx.android.synthetic.`at-daiho`.fragment_tutorial.*

class TutorialActivity : AppCompatActivity() {

    companion object{
        private const val POSITION_LAST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        setupViewPagerAdapter()
        btNextClickListener()
        viewPagerChangeListener()
        indicatorSetup()
    }

    private fun setupViewPagerAdapter() {
        val adapter = TutorialAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private  fun indicatorSetup() {
        indicator.setViewPager(viewPager)
    }

    private fun viewPagerChangeListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == POSITION_LAST) {
                    btNext.text = getString(R.string.fragment_tutorial_next_button_title)
                } else {
                    btNext.text = getString(R.string.fragment_tutorial_skip_button_title)
                }
            }
        })
    }

    private fun btNextClickListener() {
        btNext.setOnClickListener {
            if (viewPager.currentItem == POSITION_LAST) {

            } else {

            }
        }
    }
}
