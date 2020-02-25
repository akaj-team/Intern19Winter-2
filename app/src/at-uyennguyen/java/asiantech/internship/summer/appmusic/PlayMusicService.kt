package asiantech.internship.summer.appmusic

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R

class PlayMusicService : Service() {

    var currentPos: Int = 0
    lateinit var musicDataList: ArrayList<Media>
    lateinit var mediaPlayer: MediaPlayer
    private var iBinder: IBinder = MediaBinder()
    private var NOTIFICATION_ID = "notification"
    internal var onItemClicked: (position: Int) -> Unit = {}
    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            currentPos = this.getIntExtra(PlayMusicFragment.MUSICITEMPOSITION, 0)
            musicDataList = this.getParcelableArrayListExtra(PlayMusicFragment.MUSICLIST)
        }
//        mediaNotification()
        playMusic()
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
        mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                mp?.start()
            }
        })
        mediaPlayer.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                nextMusic()
            }
        })
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun playMusic() {
        mediaPlayer.stop()
        mediaPlayer.release()
        initMediaPlayer()
        mediaPlayer.setDataSource(applicationContext, Uri.parse(musicDataList[currentPos].path))
        mediaPlayer.prepareAsync()
    }

    fun pauseMusic() {
        mediaPlayer.pause()
    }

    fun runContinueMusic() {
        mediaPlayer.start()
    }

    fun nextMusic() {
        currentPos++
        Log.i("vinh","current position" + currentPos)
        onItemClicked.invoke(currentPos)
        Log.i("vinh","current position" + currentPos)

        playMusic()
    }

    fun previousMusic() {
//        if(currentPos==0){
//            currentPos=musicDataList.size-1
//        }
        currentPos--
        playMusic()
    }

    inner class MediaBinder : Binder() {
        fun getService(): PlayMusicService {
            return this@PlayMusicService
        }
    }

    fun seekTo(progress: Int) {
        mediaPlayer.seekTo(progress)
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    fun getDuration(): Int {
        return mediaPlayer.duration
    }

    fun mediaNotification() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            var mediaNotification = NotificationChannel(NOTIFICATION_ID,"Channel 1",NotificationManager.IMPORTANCE_HIGH)
//            mediaNotification.description = "This is Channel 1"
//            var manager : NotificationManager = getSystemService(NotificationManager::class)
//            manager.createNotificationChannel(mediaNotification)
//            var notificationManager : NotificationManager
//        }
        var intent: Intent = Intent(this, PlayMusicFragment::class.java)
        var pendingIntent: PendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent), 0)

        var notification = NotificationCompat.Builder(this, NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_ipod)
                .setContentTitle("NhacCuaUyen")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)

    }
}
