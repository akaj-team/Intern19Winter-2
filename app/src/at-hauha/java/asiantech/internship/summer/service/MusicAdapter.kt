package asiantech.internship.summer.service

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song
import asiantech.internship.summer.service.model.SongUtils

class MusicAdapter(private val songList: MutableList<Song>, var mContext: Context) : RecyclerView.Adapter<MusicAdapter.SongViewHolder>() {

    private var positionSelected = -1
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        (holder as? SongViewHolder)?.binData()
    }

    internal var onItemClicked: (position: Int) -> Unit = {}

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvArtist: TextView = itemView.findViewById(R.id.tvArtist)
        private val imgSong: ImageView = itemView.findViewById(R.id.imgSong)
        private val cardViewItem: CardView = itemView.findViewById(R.id.cardViewItem)

        init {
            cardViewItem.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
                positionSelected = adapterPosition
                notifyDataSetChanged()
            }
        }

        fun binData() {
            val song = songList[adapterPosition]
            tvTitle.text = song.title
            tvArtist.text = song.artist
            var bitmap = SongUtils.songArt(Uri.parse(song.path), mContext)
            if (bitmap != null) {
                imgSong.setImageBitmap(bitmap)
            } else {
                imgSong.setImageResource(R.drawable.default_song)
            }
                if (adapterPosition == positionSelected) {
                    if (isPlaying) {
                        mediaPlayer.release()
                    }
                    var mMediaPlayer = MediaPlayer.create(mContext, Uri.parse(songList[adapterPosition].path))
                    mMediaPlayer.start()
                }
            }
        }
    }

