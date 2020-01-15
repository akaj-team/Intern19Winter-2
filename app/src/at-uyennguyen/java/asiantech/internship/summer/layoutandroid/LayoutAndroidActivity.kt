package asiantech.internship.summer.layoutandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_login.*
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_signin.*

class LayoutAndroidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_android)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val loginFragment: LoginFragment = LoginFragment()
        fragmentTransaction.add(R.id.frameLayout, loginFragment).commit()

    }

}