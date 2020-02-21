package asiantech.internship.summer.service_broadcastreceiver

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class App : Application() {

    companion object{
        const val CHANNEL_ID: String = "ForegroundServiceChannel"
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val serviceChannel  = NotificationChannel(
                    CHANNEL_ID,
                    "Example Foreground Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
