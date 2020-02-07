package asiantech.internship.summer.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import asiantech.internship.summer.R

class MyMainActiviry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.frLayout, RegisterFragment(), null)
                .addToBackStack(null)
                .commit()

    }
}
