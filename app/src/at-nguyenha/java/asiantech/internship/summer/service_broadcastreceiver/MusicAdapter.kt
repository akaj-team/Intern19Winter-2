package asiantech.internship.summer.service_broadcastreceiver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import java.util.concurrent.TimeUnit

class MusicAdapter(private val listSong: MutableList<MusicModel>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun getItemCount() = listSong.size

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        (holder as? MusicViewHolder)?.bindData()
    }

    private fun convertTimeMusic(millis: Long): String {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val musicName: TextView = itemView.findViewById(R.id.tvMusicTitle)
        private val musicDuration: TextView = itemView.findViewById(R.id.tvMusicTime)
        private val musicArtist: TextView = itemView.findViewById(R.id.tvMusicArtist)
        private var imgPlay: ImageView = itemView.findViewById(R.id.imgPlay)

        fun bindData() {
            listSong[adapterPosition].let {
                musicName.text = it.musicName
                musicDuration.text = convertTimeMusic(it.musicDuration)
                musicArtist.text = it.musicArtist
                imgPlay.setImageURI(it.musicImage)
            }
        }

    }


}