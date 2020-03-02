package asiantech.internship.summer.savedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.User


class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, SplashFragment(), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceMenuFragment(user: User) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, MenuFragment.newInstance(user), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceLoginFragment(){
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, LoginFragment(), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceRegisterFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, RegisterFragment(), null)
                .addToBackStack(null)
                .commit()
    }
    fun replaceEditProfileFragment(user: User) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, EditProfileFragment.newInstance(user), null)
                .addToBackStack(null)
                .commit()
    }
}