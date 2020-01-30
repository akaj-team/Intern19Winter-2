package asiantech.internship.summer.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.fragment_login.*
import kotlinx.android.synthetic.`at-nguyenha`.fragment_register.*

class MyMainActiviry : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        replaceFragment(RegisterFragment())

    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frLayout, fragment, null)
                .addToBackStack(null)
                .commit()
    }
}
