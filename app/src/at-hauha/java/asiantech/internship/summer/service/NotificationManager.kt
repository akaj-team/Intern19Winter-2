package asiantech.internship.summer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R
import asiantech.internship.summer.service.Utils.NEXT_ACTION
import asiantech.internship.summer.service.Utils.PLAY_ACTION
import asiantech.internship.summer.service.Utils.PREV_ACTION

class NotificationManager internal constructor(private val musicService: PlayMusicService) {

    private val manager: NotificationManager = musicService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    var builder: NotificationCompat.Builder? = null
    private val context: Context = musicService.baseContext
    private var mediaSession: MediaSessionCompat? = null

    companion object {
        private const val REQUEST_CODE = 101
        private const val CHANNEL_ID = "action.CHANNEL_ID"
    }

    private fun intentAction(action: String): PendingIntent {
        val broadCastIntent = Intent(context, NotificationBroadCast::class.java)
        broadCastIntent.action = action
        return PendingIntent.getBroadcast(context, REQUEST_CODE, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun createNotification(): Notification? {
        val song = musicService.currentSong()
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, MusicActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                context,
                0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder?.apply {
            setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                            .setMediaSession(mediaSession?.sessionToken)
                            .setShowActionsInCompactView(0, 1, 2)
            )
            setContentTitle(song.title)
            setSmallIcon(R.drawable.ic_music_note_white_24dp)
            setLargeIcon(Utils.songArt(Uri.parse(song.path), context))
            setContentText(song.artist)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            addAction(notificationAction(PREV_ACTION))
            addAction(notificationAction(PLAY_ACTION))
            addAction(notificationAction(NEXT_ACTION))
        }
        return builder?.build()
    }

    private fun notificationAction(action: String): NotificationCompat.Action {

        val icon: Int = when (action) {
            PREV_ACTION -> R.drawable.ic_skip_previous_pink_400_24dp
            PLAY_ACTION -> R.drawable.play_button
            NEXT_ACTION -> R.drawable.next_button
            else -> R.drawable.ic_skip_previous_pink_400_24dp
        }
        return NotificationCompat.Action.Builder(
                icon,
                action,
                intentAction(action)
        ).build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(serviceChannel)
        }
    }

}
