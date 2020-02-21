package asiantech.internship.summer.service_broadcastReceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
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

    private lateinit var audioManager: AudioManager
    private var binder = LocalBinder()
    private var positionSong: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private val music = mutableListOf<Music>()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getIntExtra("inputExtra", 0)
        positionSong = input
        createNotificationChannel()
        val notificationIntent = Intent(this, MusicFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service Kotlin Example")
                .setSmallIcon(R.drawable.ic_music)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_music))
                .setContentIntent(pendingIntent)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
                .build()
        startForeground(1, notification)
        playMusic()
        when (intent.action) {
            MusicAction().TOTALTIME ->{

            }
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Music Service started by user.", Toast.LENGTH_LONG).show();
        music.addAll(MusicData.getMusic(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        Toast.makeText(this, "Music Service destroyed by user.", Toast.LENGTH_LONG).show();
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

    internal fun playNext() {
        positionSong++
        playMusic()
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun playSong() {
        mediaPlayer?.start()
    }

    fun getPosition(): Int {
        return positionSong
    }

    fun getCurrentDuration(): Int? {
        return mediaPlayer?.currentPosition
    }

    internal fun getDuration(): Int? {
        return mediaPlayer?.duration
    }

    override fun onPrepared(p0: MediaPlayer?) {
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {
    }
}
