package asiantech.internship.summer.service_broadcastReceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import asiantech.internship.summer.R

class ForegroundService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    companion object {
        const val CHANNEL_ID = "ForegroundService Kotlin"
        private const val DEFAULT_VALUE_POSITION = 0
        fun startService(context: Context, message: Int) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    private val filter = IntentFilter()
    private var binder = LocalBinder()
    private var positionSong: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private val music = mutableListOf<Music>()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getIntExtra("inputExtra", DEFAULT_VALUE_POSITION)
        if (input != DEFAULT_VALUE_POSITION) {
            positionSong = input
        }
        Log.i("XXX", positionSong.toString())
        createNotificationChannel()
        playMusic()
        Log.i("XXX", "Foreground action: ${intent.action}")
        when (intent?.action) {
            MusicAction().NEXT -> {
                playNext()
            }
            MusicAction().PAUSE -> {
                pauseMusic()
            }
            MusicAction().PLAY -> {
                playSong()
            }
            MusicAction().PRIVIOUS -> {
                playPrevious()
            }
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(music[positionSong].name)
                .setSmallIcon(R.drawable.ic_music)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_music))
                .addAction(R.drawable.ic_skip_previous_red_a400_24dp, "previous", createAction(MusicAction().PRIVIOUS))
                .addAction(R.drawable.ic_play_circle_outline_red_a400_24dp, "play", createAction(MusicAction().PAUSE))
                .addAction(R.drawable.ic_skip_next_red_a400_24dp, "next", createAction(MusicAction().NEXT))
                .addAction(R.drawable.ic_close_black_24dp, "close", createAction(MusicAction().CLOSE))
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1, 2, 3))
                .build()
        startForeground(1, notification)
        createIntentFilter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    private fun createAction(action: String): PendingIntent? {
        val intent = Intent()
        intent.action = action
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createIntentFilter() {
        filter.apply {
            addAction(MusicAction().PRIVIOUS)
            addAction(MusicAction().PAUSE)
            addAction(MusicAction().NEXT)
            addAction(MusicAction().CLOSE)
        }
        registerReceiver(MusicReceiver(this), filter)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Music Service started by user.", Toast.LENGTH_LONG).show()
        music.addAll(MusicData.getMusic(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        Toast.makeText(this, "Music Service destroyed by user.", Toast.LENGTH_LONG).show()
    }

    inner class LocalBinder : Binder() {
        internal val getService: ForegroundService
            get() = this@ForegroundService
    }

    private fun initMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer()
    }

    private fun playMusic() {
        initMediaPlayer()
        mediaPlayer?.setDataSource(this, music[positionSong].uri)
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    private fun playNext() {
        positionSong++
        playMusic()
    }

    private fun playPrevious() {
        positionSong--
        playMusic()
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
    }

    private fun playSong() {
        mediaPlayer?.start()
    }

    internal fun getPosition(): Int {
        return positionSong
    }

    internal fun seekTo(currentDuration: Int) {
        mediaPlayer?.seekTo(currentDuration)
    }

    internal fun getCurrentDuration(): Int? {
        return mediaPlayer?.currentPosition
    }

    override fun onPrepared(p0: MediaPlayer?) {
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {
    }
}
