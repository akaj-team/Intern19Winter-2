package asiantech.internship.summer.layoutandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class LayoutAndroidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_android)
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        val loginFragment: LoginFragment = LoginFragment()
//        fragmentTransaction.add(R.id.frameLayout, loginFragment).commit()
        val registerFragment: RegisterFragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, registerFragment)
                .addToBackStack(null)
                .commit()
    }

//    override fun onAttachFragment(fragment: Fragment?) {
//        super.onAttachFragment(fragment)
//        (fragment as EditProfileFragment).requestPermissions(arrayOf(Manifest.permission.CAMERA), fragment.img)
//    }
}
