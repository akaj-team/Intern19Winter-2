package asiantech.internship.summer.service_broadcastReceiver

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import java.io.IOException
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf


@SuppressLint("Registered")
class MediaPlayerService : Service(), MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener {
    private var mediaPlayer: MediaPlayer? = null
    private var resumePosition = 0
    private lateinit var audioManager: AudioManager
    //path to the audio file
    private var mediaFile: String? = null
    private val iBinder: IBinder = LocalBinder()

    override fun onBind(p0: Intent?): IBinder? {
        return iBinder
    }

    override fun onCompletion(p0: MediaPlayer?) {
        stopMedia()
        stopSelf()
    }

    override fun onPrepared(p0: MediaPlayer?) {
        playMedia()
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
//        when(p1){
//            MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK
//        }
        return false
    }

    override fun onSeekComplete(p0: MediaPlayer?) {
    }

    override fun onInfo(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try { //An audio file is passed to the service through putExtra();
            mediaFile = intent!!.extras!!.getString("media")!!
        } catch (e: NullPointerException) {
            stopSelf()
        }

        //Request audio focus
        //Request audio focus
        if (!requestAudioFocus()) { //Could not gain focus
            stopSelf()
        }

        if (mediaFile != null && mediaFile !== "") initMediaPlayer()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onAudioFocusChange(p0: Int) {
        when (p0) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                if (mediaPlayer == null) {
                    initMediaPlayer()
                } else if (mediaPlayer?.isPlaying!!) {
                    mediaPlayer?.start()
                }
                mediaPlayer?.setVolume(1.0f, 1.0f)
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                if (mediaPlayer?.isPlaying!!) mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                if (mediaPlayer?.isPlaying!!) {
                    mediaPlayer?.pause()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                if (mediaPlayer?.isPlaying!!) {
                    mediaPlayer?.setVolume(0.1f, 0.1f)
                }
            }
        }
    }

    class LocalBinder : Binder() {
        fun getService(): MediaPlayerService {
            return MediaPlayerService()
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setOnErrorListener(this)
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.setOnBufferingUpdateListener(this)
        mediaPlayer?.setOnSeekCompleteListener(this)
        mediaPlayer?.setOnInfoListener(this)
        //Reset so that the MediaPlayer is not pointing to another data source
        mediaPlayer?.reset()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try { // Set the data source to the mediaFile location
            mediaPlayer!!.setDataSource(mediaFile)
        } catch (e: IOException) {
            e.printStackTrace()
            stopSelf()
        }
        mediaPlayer!!.prepareAsync()
    }

    private fun playMedia() {
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
        }
    }

    private fun stopMedia() {
        if (mediaPlayer == null) return
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
    }

    private fun pauseMedia() {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            resumePosition = mediaPlayer!!.currentPosition
        }
    }

    private fun resumeMedia() {
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.seekTo(resumePosition)
            mediaPlayer!!.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            stopMedia()
            mediaPlayer?.release()
        }
        removeAudioFocus()
    }

    private fun requestAudioFocus(): Boolean {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        //Could not gain focus
    }

    private fun removeAudioFocus(): Boolean {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                audioManager.abandonAudioFocus(this)
    }
}
