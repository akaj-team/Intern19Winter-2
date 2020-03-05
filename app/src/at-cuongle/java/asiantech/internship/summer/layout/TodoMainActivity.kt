package asiantech.internship.summer.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R

class TodoMainActivity : AppCompatActivity() {
    companion object {
        private const val ARG_IS_LOGIN = "isLogin"
        private const val ARG_PREFERENCES = "MyPref"
    }

    protected var a = ""
    private var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_main)
        val sharedPreferences = this.getSharedPreferences(ARG_PREFERENCES, 0)
        isLogin = sharedPreferences?.getBoolean(ARG_IS_LOGIN, false) ?: false
        if (isLogin) {
            replaceFragment(MainScreenFragment())
        } else {
            replaceFragment(UserLoginFragment())
        }
    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, fragment, null)
                .addToBackStack(null)
                .commit()
    }
}
