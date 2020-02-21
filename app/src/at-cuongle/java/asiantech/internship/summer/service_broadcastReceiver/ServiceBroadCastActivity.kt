package asiantech.internship.summer.service_broadcastReceiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import java.util.concurrent.TimeUnit

class ServiceBroadCastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_broad_cast)
        replaceFragment(MusicFragment())
    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.clContainer, fragment, null)
                .addToBackStack(null)
                .commit()
    }

    internal fun toMin(millis: Long): String {
        return resources.getString(R.string.tv_duration, TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }
}
