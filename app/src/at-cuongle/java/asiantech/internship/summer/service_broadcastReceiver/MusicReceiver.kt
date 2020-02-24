package asiantech.internship.summer.service_broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

open class MusicReceiver(private val foregroundService: ForegroundService) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("XXX", "Music Receiver action: ${intent?.action}")
        val intentSendAction = Intent(context, ForegroundService::class.java)
        when (intent?.action) {
            MusicAction().NEXT -> {
                intentSendAction.action = MusicAction().NEXT
                intentSendAction.putExtra(MusicAction().NEXT,1)
                context?.sendBroadcast(intentSendAction)
            }
            MusicAction().NOTIFICATION_PAUSE -> {
                intentSendAction.action = intent.action
//                context?.startService(intentSendAction)
            }
            MusicAction().NOTIFICATION_PRIVIOUS -> {
                intentSendAction.action = intent.action
//                context?.startService(intentSendAction)
            }
            MusicAction().NOTIFICATION_PLAY -> {
                intentSendAction.action = intent.action
//                context?.startService(intentSendAction)
            }
            MusicAction().NOTIFICATION_CLOSE ->{
                if (context != null) {
                    ForegroundService.stopService(context)
                }
            }
        }
    }
}
