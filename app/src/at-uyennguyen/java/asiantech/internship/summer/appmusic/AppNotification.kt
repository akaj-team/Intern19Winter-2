package asiantech.internship.summer.appmusic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R

class AppNotification(playMusicService: PlayMusicService) {

    companion object {
        private const val BROADCAST_CODE = 111
        private const val CHANNEL_ID = "musicnotification"
        private const val PRIVIOUS = "privious"
        private const val NEXT = "next"
        private const val PLAY = "play"
    }

    private var builder: NotificationCompat.Builder? = null
    private var context: Context = playMusicService.baseContext
    private var sessionCompat: MediaSessionCompat? = null
    private var notificationManager: NotificationManager = playMusicService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotifi(media: Media, isPlay: Boolean): Notification {
        val filter = IntentFilter()
        filter.apply {
            addAction(PLAY)
            addAction(NEXT)
            addAction(PRIVIOUS)
        }
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
            addAction(notificationAction(PRIVIOUS, isPlay))
            addAction(notificationAction(PLAY, isPlay))
            addAction(notificationAction(NEXT, isPlay))
        }
        return builder!!.build()
    }

    private fun intentAction(action: String): PendingIntent {
        val broadCastIntent = Intent()
        broadCastIntent.action = action
        return PendingIntent.getBroadcast(context, BROADCAST_CODE, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val service = NotificationChannel(CHANNEL_ID, "foreground service", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(service)
        }
    }

    private fun notificationAction(action: String, isPlay: Boolean): NotificationCompat.Action {
        val icon: Int = when (action) {
            PRIVIOUS -> R.drawable.ic_skip_previous_white_36dp
            PLAY -> if (!isPlay) {
                R.drawable.ic_pause_white_36dp
            } else {
                R.drawable.ic_play_circle
            }
            NEXT -> R.drawable.ic_skip_next_white_36dp
            else -> R.drawable.ic_skip_previous_white_36dp
        }
        return NotificationCompat.Action.Builder(icon, action, intentAction(action)).build()
    }
}
