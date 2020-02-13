package asiantech.internship.summer.viewPager_tabLayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_home.*


class HomeActivity : AppCompatActivity() {
    private var currentPosition = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val adapter = HomeAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
        tvAction.text = "Skip"
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
                    1 -> tvAction.text = "Skip"
                    2 -> tvAction.text = "Next"
                }
                currentPosition = position + 1
                if (position == 2) {
                    tvAction.setOnClickListener {
                        Log.i("XXX", "Replace")
//                        replaceFragment(TabLayoutFragment())
                        val intent = Intent(this@HomeActivity, TabLayoutActivity::class.java)
                        startActivity(intent)
                    }
                }

            }

        })

        tvAction.setOnClickListener {
            Log.i("XXX", "Current$currentPosition")
            viewPager.currentItem = currentPosition
        }
    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.llContainer, fragment, null)
                .addToBackStack(null)
                .commit()
    }
}
