package asiantech.internship.summer.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class NewFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerViewActivity = NewFeedFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.relativeLayout, recyclerViewActivity)
                .commit()
    }
}
