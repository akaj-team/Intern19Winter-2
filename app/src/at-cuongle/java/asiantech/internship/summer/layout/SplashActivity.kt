package asiantech.internship.summer.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import asiantech.internship.summer.R

class SplashActivity : AppCompatActivity() {
    companion object{
        private const val SPLASH_DELAY: Long = 3000
    }
    private var delayHandler: Handler? = null
    private val runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, LayoutMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)
    }
    public override fun onDestroy() {

        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(runnable)
        }
        super.onDestroy()
    }
}
