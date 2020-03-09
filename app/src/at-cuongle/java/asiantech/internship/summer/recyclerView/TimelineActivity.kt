package asiantech.internship.summer.recyclerView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R

class TimelineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        replaceFragment(TimeLineFragment())
    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, fragment, null)
                .addToBackStack(null)
                .commit()
    }
}
