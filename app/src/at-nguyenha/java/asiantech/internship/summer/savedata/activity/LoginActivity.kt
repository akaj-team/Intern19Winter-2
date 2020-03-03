package asiantech.internship.summer.savedata.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.LoginAdapter
import kotlinx.android.synthetic.`at-nguyenha`.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val adapterLogin = LoginAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        vpLoginRegister.adapter = adapterLogin
        tabLoginRegister.setupWithViewPager(vpLoginRegister)
    }
}
