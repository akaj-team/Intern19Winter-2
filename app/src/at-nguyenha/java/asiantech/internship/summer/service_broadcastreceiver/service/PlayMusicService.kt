package asiantech.internship.summer.service_broadcastreceiver.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PlayMusicService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
