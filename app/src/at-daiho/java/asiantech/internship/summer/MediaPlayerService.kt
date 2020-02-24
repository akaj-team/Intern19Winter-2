package asiantech.internship.summer

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import java.io.IOException


class MediaPlayerService : IntentService("MediaPlayerService"), MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private var mediaFile: Uri? = null
    private var resumePosition = 0

    private var binder: IBinder = MediaPlayerBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("xxx", "onHandleIntent")
        Log.d("xxx", mediaFile.toString())
        Log.d("xxx", mediaPlayer?.isPlaying.toString())
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        if (mediaFile == null) {
            return
        }
        val mediaP = mediaPlayer!!
        mediaP.setOnCompletionListener(this)
        mediaP.setOnPreparedListener(this)
        mediaP.reset()
    }

    private  fun initMediaPlayerData() {
        if (mediaPlayer != null && mediaFile != null) {
            mediaPlayer?.setDataSource(applicationContext, mediaFile!!)
            mediaPlayer?.prepareAsync()
        }
    }

    fun playMedia() {
        if (mediaPlayer != null) {
            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer?.start()
            }
        }
    }

    fun stopMedia() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.stop()
            }
        }
    }

    fun pauseMedia() {
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer?.pause()
            resumePosition = mediaPlayer?.currentPosition ?: 0
        }
    }

    fun resumeMedia() {
        if (mediaPlayer != null) {
            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer?.seekTo(resumePosition)
                mediaPlayer?.start()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            //An audio file is passed to the service through putExtra();
            val uriString = intent?.extras?.getString("media") ?: ""
            mediaFile = Uri.parse(uriString)
            initMediaPlayerData()
            playMedia()
            Log.d("xxy", mediaFile.toString())
            return START_NOT_STICKY
        } catch (e: Exception) {
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onCompletion(mp: MediaPlayer?) {
        //Invoked when playback of a media source has completed.
        stopMedia()
        //stop the service
        stopSelf()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        playMedia()
    }

    inner class MediaPlayerBinder: Binder() {
        fun getService(): MediaPlayerService {
            return this@MediaPlayerService
        }
    }
}
