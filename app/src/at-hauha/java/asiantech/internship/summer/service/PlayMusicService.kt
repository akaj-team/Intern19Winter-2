package asiantech.internship.summer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.widget.SeekBar
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R
import asiantech.internship.summer.service.Utils.DEFAUlT_POS
import asiantech.internship.summer.service.Utils.POSITION
import asiantech.internship.summer.service.Utils.SONGLIST
import asiantech.internship.summer.service.model.Song

class PlayMusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private lateinit var songList: ArrayList<Song>
    private var currentPos = -1
    private var mediaPlayer: MediaPlayer? = null
    private var musicBinder = MusicBinder()
    private var mediaSession: MediaSessionCompat? = null
    var context: Context? = null
    private var notificationReceiver: NotificationReceiver? = null

    companion object {
        internal const val PLAY_ACTION = "action.PLAYPAUSE"
        internal const val NEXT_ACTION = "action.NEXT"
        internal const val PREV_ACTION = "action.PREV"
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
        playMusic()
        notificationReceiver = NotificationReceiver(this)
        val notification = notificationReceiver?.createNotification()
        startForeground(1,notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        nextSong()
    }

    inner class MusicBinder : Binder() {
        internal val getService: PlayMusicService
            get() = this@PlayMusicService
    }

    fun currentSong() : Song{
        return songList[currentPos]
    }

    private fun playMusic() {
        initMediaPlayer()
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    fun shuffleMusic(){
        songList.shuffle()
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
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setDataSource(songList[currentPos].path)
    }

    fun getPosition(): Int {
        return currentPos
    }

    fun seekToMedia(seekBar: SeekBar) {
        mediaPlayer?.seekTo(seekBar.progress)
    }

}