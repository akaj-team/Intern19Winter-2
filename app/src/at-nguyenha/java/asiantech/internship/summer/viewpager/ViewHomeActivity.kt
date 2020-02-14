package asiantech.internship.summer.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.activity_view_home.*

class ViewHomeActivity : AppCompatActivity() {

    private val adapter = ViewHomeAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_home)

        vpHome.adapter = adapter
        tabHome.setupWithViewPager(vpHome)
    }
}
