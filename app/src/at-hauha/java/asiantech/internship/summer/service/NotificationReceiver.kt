package asiantech.internship.summer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.session.MediaSessionManager
import android.net.Uri
import android.os.Build
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.MainActivity
import asiantech.internship.summer.R
import android.app.PendingIntent as PendingIntent

class NotificationReceiver internal constructor(private val musicService: PlayMusicService) {
    val manager: NotificationManager = musicService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    var builder: NotificationCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null
    private var mediaSessionManager: MediaSessionManager? = null
    private var transportControls: MediaControllerCompat.TransportControls? = null
    private val context: Context = musicService.baseContext

    companion object {
        private const val REQUEST_CODE = 101
        private const val CHANNEL_ID = "action.CHANNEL_ID"
    }

    private fun playerAction(action: String): PendingIntent {
        val pauseIntent = Intent()
        pauseIntent.action = action
        return PendingIntent.getBroadcast(musicService, REQUEST_CODE, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun createNotification(): Notification? {
        val song = musicService.currentSong()
        builder = NotificationCompat.Builder(musicService, CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                musicService,
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
            addAction(notificationAction(PlayMusicService.PREV_ACTION))
            addAction(notificationAction(PlayMusicService.PLAY_ACTION))
            addAction(notificationAction(PlayMusicService.NEXT_ACTION))
        }
        return builder?.build()
    }

    private fun notificationAction(action: String): NotificationCompat.Action {

        val icon: Int = when (action) {
            PlayMusicService.PREV_ACTION -> R.drawable.ic_skip_previous_pink_400_24dp
            PlayMusicService.PLAY_ACTION -> R.drawable.play_button
            PlayMusicService.NEXT_ACTION -> R.drawable.next_button
            else -> R.drawable.ic_skip_previous_pink_400_24dp
        }
        return NotificationCompat.Action.Builder(
                icon,
                action,
                playerAction(action)
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