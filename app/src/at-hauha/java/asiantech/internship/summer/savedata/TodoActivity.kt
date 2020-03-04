package asiantech.internship.summer.savedata

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.User


class TodoActivity : AppCompatActivity() {
    private var logined: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        getShareReference()
        if (logined != Utils.DEFAULT_ID) {
            replaceMenuFragment(logined)
        } else {
            replaceLoginFragment()
        }
    }

    fun replaceMenuFragment(id: Int) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, MenuFragment.newInstance(id), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceLoginFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, LoginFragment(), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceRegisterFragment(uri: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, RegisterFragment.newInstance(uri), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceEditProfileFragment(user: User) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, EditProfileFragment.newInstance(user), null)
                .addToBackStack(null)
                .commit()
    }

    fun replacePictureFragment(user: User) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, PictureEditFragment.newInstance(user), null)
                .addToBackStack(null)
                .commit()
    }

    fun replaceEditPictureFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, PictureFragment(), null)
                .addToBackStack(null)
                .commit()
    }

    private fun getShareReference() {
        val sharedPreferences = this
                .getSharedPreferences(Utils.SHARE_REF, Context.MODE_PRIVATE)
        logined = sharedPreferences.getInt(Utils.PUT_ID, Utils.DEFAULT_ID)
    }
}