package asiantech.internship.summer.service_broadcastreceiver

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
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import asiantech.internship.summer.service_broadcastreceiver.model.Units
import asiantech.internship.summer.service_broadcastreceiver.model.Units.ACTION_PLAY_PAUSE
import asiantech.internship.summer.service_broadcastreceiver.model.Units.ACTION_PREVIOUS
import asiantech.internship.summer.service_broadcastreceiver.model.Units.ACTION_SKIP_NEXT
import asiantech.internship.summer.service_broadcastreceiver.service.PlayMusicService

class Notification(playMusicService: PlayMusicService) {
    companion object {
        private const val REQUEST_CODE = 101
        private const val CHANNEL_ID = "CHANNEL_ID"
    }

    private var builder: NotificationCompat.Builder? = null
    private var context: Context = playMusicService.baseContext
    private var session: MediaSessionCompat? = null
    private var manager: NotificationManager = playMusicService
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun intentAction(action: String): PendingIntent {
        val broadCastIntent = Intent()
        broadCastIntent.action = action
        return PendingIntent.getBroadcast(context, REQUEST_CODE, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun createNotification(music: MusicModel, isPlaying: Boolean): Notification? {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, MyMainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                context,
                0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder?.apply {
            setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                            .setMediaSession(session?.sessionToken)
                            .setShowActionsInCompactView(0, 1, 2)
            )
            setContentTitle(music.musicName)
            setSmallIcon(R.drawable.ic_music)
            setLargeIcon(Units.songArt(Uri.parse(music.path), context))
            setContentText(music.musicArtist)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            addAction(notificationAction(ACTION_PREVIOUS, isPlaying))
            addAction(notificationAction(ACTION_PLAY_PAUSE, isPlaying))
            addAction(notificationAction(ACTION_SKIP_NEXT, isPlaying))
        }
        return builder?.build()
    }

    private fun notificationAction(action: String, isPlaying: Boolean): NotificationCompat.Action {

        val icon: Int = when (action) {
            ACTION_PREVIOUS -> R.drawable.ic_previous
            ACTION_PLAY_PAUSE -> if (isPlaying) {
                R.drawable.ic_play
            } else {
                R.drawable.ic_pause
            }
            ACTION_SKIP_NEXT -> R.drawable.ic_next
            else -> R.drawable.ic_previous
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