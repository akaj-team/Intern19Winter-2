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
import java.io.IOException


class MediaPlayerService : IntentService("MediaPlayerService"), MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnBufferingUpdateListener {

    companion object {
        val shared: MediaPlayerService = MediaPlayerService()
    }

    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private var mediaFile: Uri? = null
    private var resumePosition = 0

    private lateinit var audioManager: AudioManager
    private var audioFocusRequest: AudioFocusRequest? = null
    private lateinit var onAudioFocusChange: AudioManager.OnAudioFocusChangeListener

    override fun onHandleIntent(intent: Intent?) {

    }

    override fun onCreate() {
        super.onCreate()
        requestAudioFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeAudioFocus()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setOnErrorListener(this)
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.setOnBufferingUpdateListener(this)
        mediaPlayer?.setOnSeekCompleteListener(this)
        mediaPlayer?.reset()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            // Set the data source to the mediaFile location
            mediaPlayer?.setDataSource(this, mediaFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
            stopSelf()
        }
        mediaPlayer?.prepareAsync()
    }


    private fun requestAudioFocus(): Boolean {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        onAudioFocusChange = object : AudioManager.OnAudioFocusChangeListener {
            override fun onAudioFocusChange(focusChange: Int) {
                when (focusChange) {
                    // resume playback
                    AudioManager.AUDIOFOCUS_GAIN -> {
                        if (mediaPlayer == null)
                            initMediaPlayer()
                        else if (!mediaPlayer!!.isPlaying)
                            mediaPlayer?.start()
                        mediaPlayer?.setVolume(1.0f, 1.0f)
                    }
                    AudioManager.AUDIOFOCUS_LOSS -> {
                        // Lost focus for an unbounded amount of time: stop playback and release media player
                        if (mediaPlayer != null) {
                            if (mediaPlayer!!.isPlaying)
                                mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                        }
                    }
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ->
                        // Lost focus for a short time, but we have to stop
                        if (mediaPlayer != null) {
                            // playback. We don't release the media player because playback is likely to resume
                            if (mediaPlayer!!.isPlaying) {
                                mediaPlayer?.pause()
                            }
                        }
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->
                        // Lost focus for a short time, but it's ok to keep playing  at an attenuated level
                        if (mediaPlayer != null) {
                            if (mediaPlayer!!.isPlaying) {
                                mediaPlayer?.setVolume(0.1f, 0.1f)
                            }
                        }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(onAudioFocusChange)
                    .build()
            val result = audioManager.requestAudioFocus(audioFocusRequest!!)
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        } else {
            val result = audioManager.requestAudioFocus(onAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

    private fun removeAudioFocus(): Boolean {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.abandonAudioFocus(onAudioFocusChange)
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
        } catch (e: Exception) {
            stopSelf()
        }
        //Request audio focus
        if (!requestAudioFocus()) {
            //Could not gain focus
            stopSelf()
        }
        if (mediaFile != null)
            initMediaPlayer()
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

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onSeekComplete(mp: MediaPlayer?) {

    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {

    }
}
