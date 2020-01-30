package asiantech.internship.summer.savedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R


class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, SplashFragment(), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceMenuFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, MenuFragment(), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceLoginFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, LoginFragment(), null)
                .addToBackStack(null)
                .commit()
    }
}