package asiantech.internship.summer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R
import asiantech.internship.summer.service.Utils.DEFAUlT_POS
import asiantech.internship.summer.service.Utils.POSITION
import asiantech.internship.summer.service.Utils.SONGLIST
import asiantech.internship.summer.service.model.Song

class PlayMusicService : Service(), MediaPlayer.OnPreparedListener {

    private lateinit var songList: ArrayList<Song>
    private var currentPos = -1
    private var mediaPlayer: MediaPlayer? = null
    private var musicBinder = MusicBinder()
    private var mediaSession: MediaSessionCompat? = null

    companion object {
        internal const val PLAY_ACTION = "action.PLAYPAUSE"
        internal const val NEXT_ACTION = "action.NEXT"
        internal const val PREV_ACTION = "action.PREV"
        private const val CHANNEL_ID = "action.CHANNEL_ID"
        private const val REQUEST_CODE = 100
        fun getMusicDataIntent(context: Context, songList: ArrayList<Song>, currentPos: Int): Intent {
            val musicDataIntent = Intent(context, PlayMusicService::class.java)
            musicDataIntent.apply {
                putParcelableArrayListExtra(SONGLIST, songList)
                putExtra(POSITION, currentPos)
            }
            return musicDataIntent
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return musicBinder
    }

    override fun onPrepared(mp: MediaPlayer?) {

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            songList = getParcelableArrayListExtra<Song>(SONGLIST) as ArrayList<Song>
            currentPos = getIntExtra(POSITION, DEFAUlT_POS)
        }
        createNotificationChannel()
        val pendingIntent = PendingIntent.getActivity(
                this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        playMusic()
        val song = songList[currentPos]
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession?.sessionToken)
                .setShowActionsInCompactView(0, 1, 2)
        )
                .setContentTitle(song.title)
                .setSmallIcon(R.drawable.default_song)
                .setLargeIcon(Utils.songArt(Uri.parse(song.path), baseContext))
                .setContentText(song.title)
                .setContentIntent(pendingIntent)
                .addAction(notificationAction(PREV_ACTION))
                .addAction(notificationAction(PLAY_ACTION))
                .addAction(notificationAction(NEXT_ACTION))
                .build()
        startForeground(1, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun playerAction(action: String): PendingIntent {
        val pauseIntent = Intent()
        pauseIntent.action = action
        return PendingIntent.getBroadcast(this, REQUEST_CODE, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun notificationAction(action: String): NotificationCompat.Action {

        val icon: Int = when (action) {
            PREV_ACTION -> R.drawable.ic_skip_previous_pink_400_24dp
            PLAY_ACTION -> R.drawable.play_button
            NEXT_ACTION -> R.drawable.next_button
            else -> R.drawable.ic_skip_previous_pink_400_24dp
        }
        return NotificationCompat.Action.Builder(icon, action, playerAction(action)).build()
    }

    inner class MusicBinder : Binder() {
        internal val getService: PlayMusicService
            get() = this@PlayMusicService
    }

    private fun playMusic() {
        initMediaPlayer()
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    fun playSong() {
        mediaPlayer?.start()
    }

    fun nextSong() {
        currentPos++
        if (currentPos >= songList.size) {
            currentPos = DEFAUlT_POS
        }
        playMusic()
    }

    fun prevSong() {
        currentPos--
        if (currentPos < 0) run {
            currentPos = songList.size - 1
        }
        playMusic()
    }


    fun currentPosition(): Int? {
        return mediaPlayer?.currentPosition
    }

    fun pauseSong() {
        mediaPlayer?.pause()
    }

    private fun initMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(songList[currentPos].path)
    }

    fun getPosition(): Int {
        return currentPos
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

}