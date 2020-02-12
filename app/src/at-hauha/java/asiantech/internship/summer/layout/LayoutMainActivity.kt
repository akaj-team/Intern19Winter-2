package asiantech.internship.summer.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.RegistryFragment.Companion.newInstance

class LayoutMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_main)
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, newInstance("Nich Evans", "user@gmail.com"), null)
                .addToBackStack(null)
                .commit()
    }
}
