package asiantech.internship.summer.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.FragmentLogin.Companion.getInstance

class LayoutMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.flContainer,getInstance(),null)
                .addToBackStack(null)
                .commit()
    }
}
