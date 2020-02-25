package asiantech.internship.summer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.SeekBar
import asiantech.internship.summer.service.Utils.DEFAUlT_POS
import asiantech.internship.summer.service.Utils.POSITION
import asiantech.internship.summer.service.Utils.SONGLIST
import asiantech.internship.summer.service.model.Song

class PlayMusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private lateinit var songList: ArrayList<Song>
    private lateinit var songShuffle: ArrayList<Song>
    private lateinit var songs: ArrayList<Song>
    private var position = -1
    private var mediaPlayer: MediaPlayer? = null
    private var musicBinder = MusicBinder()
    private var isPlaying = false
    private var isLoop = false
    private var isReplay = false
    private var replay = 0

    companion object {
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
            position = getIntExtra(POSITION, DEFAUlT_POS)
        }
        songs = songList
        playMusic()
        return START_NOT_STICKY
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (isLoop) {
            playMusic()
        } else {
            if (isReplay && replay < 1) {
                replay++
                playMusic()
            } else {
                mediaPlayer?.reset()
                nextSong()
                isReplay = false
            }
        }
    }

    inner class MusicBinder : Binder() {
        internal val getService: PlayMusicService
            get() = this@PlayMusicService
    }

    private fun playMusic() {
        initMediaPlayer()
        mediaPlayer?.prepare()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
        isPlaying = true
    }

    fun loopMusic() {
        isLoop = true
    }

    fun isReplay() {
        replay = 0
        isReplay = true
    }

    fun shuffleMusic() {
        songList.shuffle()
    }

    fun unShuffle() {
        songList = songs
    }

    fun playSong() {
        mediaPlayer?.start()
        isPlaying = true
    }

    fun isPlaying() = isPlaying

    fun getIsReplay() = isReplay

    fun nextSong() {
        position++
        if (position >= songList.size) {
            position = DEFAUlT_POS
        }
        playMusic()
    }

    fun prevSong() {
        position--
        if (position < 0) run {
            position = songList.size - 1
        }
        playMusic()
    }

    fun currentPosition(): Int? {
        return mediaPlayer?.currentPosition
    }

    fun pauseSong() {
        mediaPlayer?.pause()
        isPlaying = false
    }

    private fun initMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setDataSource(songList[position].path)
    }

    fun getPosition() = position

    fun seekToMedia(seekBar: SeekBar) {
        mediaPlayer?.seekTo(seekBar.progress)
    }

    fun songList(): ArrayList<Song> {
        return songList
    }

}
