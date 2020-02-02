package asiantech.internship.summer.layoutandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class LayoutAndroidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_android)
        val registerFragment: RegisterFragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, registerFragment)
                .addToBackStack(null)
                .commit()
    }
}


