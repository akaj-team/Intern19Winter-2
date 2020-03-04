package asiantech.internship.summer.savedata

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.Utils.DEFAULT_ID
import asiantech.internship.summer.savedata.Utils.PUT_ID
import asiantech.internship.summer.savedata.Utils.SHARE_REF

@SuppressLint("Registered")
@Suppress("CAST_NEVER_SUCCEEDS")
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_TIME: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        Handler().postDelayed({
            val intent = Intent(applicationContext,TodoActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME)
    }

}