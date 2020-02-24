package asiantech.internship.summer.service_broadcastreceiver.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.provider.MediaStore
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.MyMainActivity
import asiantech.internship.summer.service_broadcastreceiver.adapter.MusicAdapter
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel

@Suppress("UNREACHABLE_CODE", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class PlayMusicService : Service(), MediaPlayer.OnCompletionListener {
    override fun onCompletion(mp: MediaPlayer?) {
        mMediaPlayer?.reset()
        initNextMusic()
    }

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
        internal var isShuffle = false
        internal var isReNew = false
    }

    private var currentPosition = 0
    private var musicDataList: ArrayList<MusicModel> = ArrayList()
    private var mMediaPlayer: MediaPlayer? = null
    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        currentPosition = intent!!.getIntExtra(MusicAdapter.MUSIC_ITEM_POSSITION, 0)
        getData(intent)
        isReNew = false
        isShuffle = true
        playMedia(currentPosition)
        return START_NOT_STICKY
    }

    private fun getData(intent: Intent?) {
        musicDataList = intent!!.getParcelableArrayListExtra(MusicAdapter.MUSIC_LIST)
    }

    private fun playMedia(position: Int) {
        if (mMediaPlayer != null) {
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setDataSource(musicDataList[position].path)
        mMediaPlayer?.prepare()
        mMediaPlayer?.setOnPreparedListener {
            mMediaPlayer?.start()
        }
        createNotification()
    }

    private fun createNotification() {
        val mediaSessionCompat = MediaSessionCompat(this, "tag")
        val notificationIntent = Intent(this, MyMainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
                0,
                notificationIntent,
                0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle(musicDataList[currentPosition].musicName)
                .setContentText(musicDataList[currentPosition].musicArtist)
                .setLargeIcon(MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        Uri.parse(musicDataList[currentPosition].musicImage)))
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .addAction(R.drawable.ic_previous, "Previous", pendingIntent)
                .addAction(R.drawable.ic_play, "PlayPause", pendingIntent)
                .addAction(R.drawable.ic_next, "Next", pendingIntent)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken))
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)
    }

    fun initNextMusic() {
        currentPosition++
        if (currentPosition > musicDataList.size - 1) {
            currentPosition = 0
        }
        playMedia(currentPosition)
    }

    fun initPreviousMusic() {
        currentPosition--
        if (currentPosition < 0) {
            currentPosition = musicDataList.size - 1
        }
        playMedia(currentPosition)
    }

    fun initPlayPause() {
        if (mMediaPlayer?.isPlaying!!) {
            mMediaPlayer?.pause()
        } else {
            mMediaPlayer?.start()
        }
    }

    fun seekTo(current: Int) {
        mMediaPlayer?.seekTo(current)
    }

    fun currentPosition() = mMediaPlayer?.currentPosition

    fun isPlaying(): Boolean? = mMediaPlayer?.isPlaying

    fun initPosition(): Int = currentPosition

    inner class LocalBinder : Binder() {
        internal val getServerInstance: PlayMusicService
            get() = this@PlayMusicService
    }
}
