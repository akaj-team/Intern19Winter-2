package asiantech.internship.summer.service_broadcastReceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import asiantech.internship.summer.R
import java.lang.Exception

class ForegroundService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    companion object {
        const val CHANNEL_ID = "ForegroundService Kotlin"
        private const val DEFAULT_VALUE_POSITION = 0
        fun startService(context: Context, message: Int) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService1(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    private val filter = IntentFilter()
    private var binder = LocalBinder()
    private var positionSong: Int = 0
    private var isPlaying = false
    private var mediaPlayer: MediaPlayer? = null
    private val music = mutableListOf<Music>()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getIntExtra("inputExtra", DEFAULT_VALUE_POSITION)
        if (input != DEFAULT_VALUE_POSITION) {
            positionSong = input
            playMusic()
            isPlaying = true
        }
        Log.i("XXX", intent.action.toString())
        when (intent?.action) {
            MusicAction.NEXT -> {
                playNext()
            }
            MusicAction.PAUSE -> {
                isPlaying = false
                pauseMusic()
            }
            MusicAction.PLAY -> {
                playSong()
                isPlaying = true
            }
            MusicAction.PRIVIOUS -> {
                playPrevious()
            }
            MusicAction.SHUFFLE -> {
                shuffleMusic()
            }
            MusicAction.LOOP -> {
                loopMusic()
            }
            MusicAction.CLOSE -> {
                stopService1(ForegroundService())
            }

        }
        createNotificationChannel()
        return START_REDELIVER_INTENT
    }

    private fun createNotificationChannel() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(music[positionSong].name)
                .setSmallIcon(R.drawable.ic_music)
//                .setLargeIcon(convertUriToBitmap(Uri.parse(music[positionSong].image.toString()), ForegroundService()))
                .addAction(R.drawable.ic_skip_previous_red_a400_24dp, "previous", createAction(MusicAction.PRIVIOUS))
                .addAction(R.drawable.ic_play_circle_outline_red_a400_24dp, "play", createAction(MusicAction.PLAY))
                .addAction(R.drawable.ic_pause_circle_outline_red_a400_24dp, "pause", createAction(MusicAction.PAUSE))
                .addAction(R.drawable.ic_skip_next_red_a400_24dp, "next", createAction(MusicAction.NEXT))
                .addAction(R.drawable.ic_close_black_24dp, "close", createAction(MusicAction.CLOSE))
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1, 2, 3, 4))
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
        val intent = Intent(this, ForegroundService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, 0)
    }

    private fun createIntentFilter() {
        filter.apply {
            addAction(MusicAction.PRIVIOUS)
            addAction(MusicAction.PAUSE)
            addAction(MusicAction.NEXT)
            addAction(MusicAction.PLAY)
            addAction(MusicAction.CLOSE)
        }
        registerReceiver(MusicReceiver(), filter)
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
        mediaPlayer?.setOnCompletionListener {
            playNext()
        }
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

    private fun shuffleMusic() {
    }

    internal fun isPlaying(): Boolean {
        return isPlaying
    }

    private fun loopMusic() {
        mediaPlayer?.isLooping = true
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
//
//    private fun convertUriToBitmap(path: Uri, context: Context): Bitmap? {
//        val retriever = MediaMetadataRetriever()
//        retriever.setDataSource(context, path)
//        val byteArray = retriever.embeddedPicture
//        if (byteArray != null) {
//            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//        }
//        return null
//    }
}
