package asiantech.internship.summer.appmusic

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder

class PlayMusicService : Service() {
    var currentPos: Int = 0
    lateinit var musicDataList: ArrayList<Media>
    lateinit var mediaPlayer: MediaPlayer
    private var iBinder: IBinder = MediaBinder()
    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            currentPos = this.getIntExtra(PlayMusicFragment.MUSICITEMPOSITION, 0)
            musicDataList = this.getParcelableArrayListExtra(PlayMusicFragment.MUSICLIST)
        }
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
        playMusic()
    }

    fun previousMusic() {
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
}
