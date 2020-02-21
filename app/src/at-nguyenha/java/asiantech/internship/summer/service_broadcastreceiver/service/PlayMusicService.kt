package asiantech.internship.summer.service_broadcastreceiver.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import asiantech.internship.summer.service_broadcastreceiver.adapter.MusicAdapter
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel

@Suppress("UNREACHABLE_CODE", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class PlayMusicService : Service() {

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
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
        playMedia()
        return START_NOT_STICKY
    }

    private fun getData(intent: Intent?) {

        musicDataList = intent!!.getParcelableArrayListExtra(MusicAdapter.MUSIC_LIST)
    }

    private fun playMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
        mMediaPlayer = MediaPlayer()
        mMediaPlayer!!.setDataSource(musicDataList[currentPosition].path)
        mMediaPlayer!!.prepare()
        mMediaPlayer!!.setOnPreparedListener {
            mMediaPlayer!!.start()
        }
        createNotification()
    }

    private fun createNotification() {
        val notificationIntent = Intent(this, asiantech.internship.summer.service_broadcastreceiver.MyMainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
                0,
                notificationIntent,
                0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.parse(musicDataList[currentPosition].musicImage)))
                .setContentTitle(musicDataList[currentPosition].musicName)
                .setContentText(musicDataList[currentPosition].musicArtist)
                .setSmallIcon(asiantech.internship.summer.R.drawable.ic_music)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)
    }

    fun initNextMusic() {
        currentPosition++
        if (currentPosition > musicDataList.size - 1) {
            currentPosition = 0
        }
        playMedia()
    }

    fun initPreviousMusic() {
        currentPosition--
        if (currentPosition < 0) {
            currentPosition = musicDataList.size - 1
        }
        playMedia()
    }

    fun initPlayPause() {
        if (mMediaPlayer!!.isPlaying) {
            mMediaPlayer!!.pause()
        } else {
            mMediaPlayer!!.start()
        }
    }

    internal fun initPosition(): Int = currentPosition

    inner class LocalBinder : Binder() {
        internal val getServerInstance: PlayMusicService
            get() = this@PlayMusicService
    }
}
