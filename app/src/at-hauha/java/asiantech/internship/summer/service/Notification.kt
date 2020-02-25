package asiantech.internship.summer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R
import asiantech.internship.summer.service.Utils.NEXT_ACTION
import asiantech.internship.summer.service.Utils.PLAY_ACTION
import asiantech.internship.summer.service.Utils.PREV_ACTION
import asiantech.internship.summer.service.model.Song

class Notification(musicService: PlayMusicService) {
    companion object {
        private const val REQUEST_CODE = 101
        private const val CHANNEL_ID = "CHANNEL_ID"
    }

    private var builder: NotificationCompat.Builder? = null
    private val context: Context = musicService.baseContext
    private var session: MediaSessionCompat? = null
    private val manager: NotificationManager = musicService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun intentAction(action: String): PendingIntent {
        val broadCastIntent = Intent()
        broadCastIntent.action = action
        return PendingIntent.getBroadcast(context, REQUEST_CODE, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun createNotification(song: Song, isPlaying: Boolean): Notification? {
        val filter = IntentFilter()
        filter.apply {
            addAction(PLAY_ACTION)
            addAction(NEXT_ACTION)
            addAction(PREV_ACTION)
        }
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
                            .setMediaSession(session?.sessionToken)
                            .setShowActionsInCompactView(0, 1, 2)
            )
            setContentTitle(song.title)
            setSmallIcon(R.drawable.ic_music_note_white_24dp)
            setLargeIcon(Utils.songArt(Uri.parse(song.path), context))
            setContentText(song.artist)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            addAction(notificationAction(PREV_ACTION, isPlaying))
            addAction(notificationAction(PLAY_ACTION, isPlaying))
            addAction(notificationAction(NEXT_ACTION, isPlaying))
        }
        return builder?.build()
    }

    private fun notificationAction(action: String, isPlaying: Boolean): NotificationCompat.Action {

        val icon: Int = when (action) {
            PREV_ACTION -> R.drawable.ic_skip_previous_pink_400_24dp
            PLAY_ACTION -> if (isPlaying) {
                R.drawable.ic_pause_black_24dp

            } else {
                R.drawable.ic_play_arrow_black_24dp
            }
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
