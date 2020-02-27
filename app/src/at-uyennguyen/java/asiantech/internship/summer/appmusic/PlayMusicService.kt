package asiantech.internship.summer.appmusic

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log

class PlayMusicService : Service() {
    companion object {
        private const val PRIVIOUS = "privious"
        private const val NEXT = "next"
        private const val PLAY = "play"
        private const val PAUSE = "pause"
    }

    private var appNotification: AppNotification? = null
    private var currentPos: Int = 0
    private var listSize: Int = 0
    private var isPlay = false
    lateinit var musicDataList: ArrayList<Media>
    lateinit var mediaPlayer: MediaPlayer
    private var iBinder: IBinder = MediaBinder()
    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            currentPos = this.getIntExtra(PlayMusicFragment.MUSICITEMPOSITION, 0)
            musicDataList = this.getParcelableArrayListExtra(PlayMusicFragment.MUSICLIST)
            listSize = musicDataList.size
        }
        playMusic()
        addAction()
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
        mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                mp?.start()
            }
        })
//        mediaPlayer.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
//            override fun onCompletion(mp: MediaPlayer?) {
//                nextMusic()
//            }
//        })
    }

    private fun createNotification() {
        appNotification = AppNotification(this)
        val notification = appNotification?.createNotifi(musicDataList[currentPos], isPlay)
        this.startForeground(1, notification)
    }

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                PRIVIOUS -> {
                    isPlay = false
                    previousMusic()
                    createNotification()
                }
                NEXT -> {
                    isPlay = false
                    nextMusic()
                    createNotification()
                }
                PLAY -> if (!isPlay) {
                    pauseMusic()
                    isPlay = true
                    createNotification()
                } else {
                    runContinueMusic()
                    isPlay = false
                    createNotification()
                }
                PAUSE -> if (!isPlay) {
                    pauseMusic()
                    isPlay = true
                    createNotification()
                } else {
                    runContinueMusic()
                    isPlay = false
                    createNotification()
                }
            }
        }
    }

    private fun addAction() {
        val filter = IntentFilter()
        filter.apply {
            addAction(PRIVIOUS)
            addAction(PLAY)
            addAction(NEXT)
            addAction(if (!isPlay) {
                PAUSE
            } else {
                PLAY
            })
        }
        registerReceiver(broadcastReceiver, filter)
    }

    fun playMusic() {
        mediaPlayer.stop()
        mediaPlayer.release()
        initMediaPlayer()
        mediaPlayer.setDataSource(applicationContext, Uri.parse(musicDataList[currentPos].path))
        mediaPlayer.prepareAsync()
    }

    fun pauseMusic() {
        isPlay = true
        mediaPlayer.pause()
    }

    fun runContinueMusic() {
        mediaPlayer.start()
    }

    fun nextMusic() {
        if (currentPos == musicDataList.size - 1) {
            currentPos = 0
            playMusic()
        } else {
            currentPos++
            playMusic()
        }
    }

    fun previousMusic() {
        if (currentPos <= 0) {
            currentPos = listSize - 1
            Log.d("current", "position" + currentPos)
            playMusic()
        } else {
            currentPos--
            playMusic()
        }
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


    fun getPosition(): Int {
        return currentPos
    }
}
