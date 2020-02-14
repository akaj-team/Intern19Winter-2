package asiantech.internship.summer.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class MyMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        supportFragmentManager.beginTransaction()
                .add(R.id.frLayout, RegisterFragment(), null)
                .addToBackStack(null)
                .commit()
    }
}
