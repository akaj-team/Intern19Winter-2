package asiantech.internship.summer.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class LayoutMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, LoginFragment.getInstance(), null)
                .addToBackStack(null)
                .commit()

    }
}

