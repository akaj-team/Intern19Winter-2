package asiantech.internship.summer.appmusic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R

class AppNotification(playMusicService: PlayMusicService, var media: Media, var isPlay: Boolean) {
    companion object {
        private const val BROADCAST_CODE = 111
        private const val CHANNEL_ID = "musicnotification"
    }

    private var builder: NotificationCompat.Builder? = null
    private var context: Context = playMusicService.baseContext
    private var sessionCompat: MediaSessionCompat? = null
    private var notificationManager: NotificationManager = playMusicService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun intentAction(action: String): PendingIntent {
        var broadCastIntent = Intent()
        broadCastIntent.action = action
        return PendingIntent.getBroadcast(context, BROADCAST_CODE, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun createNotifi(): Notification {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
        notificationChannel()
        val intent = Intent(context, PlayMusicFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder?.apply {
            setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(sessionCompat?.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2))
            setContentTitle(media.nameSong)
            setSmallIcon(R.drawable.ic_ipod)
            setContentText(media.singer)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            addAction(R.drawable.ic_skip_previous_white_36dp, "PREV", pendingIntent)
            if (isPlay) {
                addAction(R.drawable.ic_play_circle, "PREV", pendingIntent)
            } else {
                addAction(R.drawable.ic_pause_white_36dp, "PREV", pendingIntent)
            }
            addAction(R.drawable.ic_skip_next_white_36dp, "PREV", pendingIntent)
        }
        return builder!!.build()
    }

    //    private fun createAction()
    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var service = NotificationChannel(CHANNEL_ID, "foreground service", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(service)
        }
    }
}