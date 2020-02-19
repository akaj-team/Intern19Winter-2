package asiantech.internship.summer.service_broadcastreceiver

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.service_broadcastreceiver.model.MusicModel
import asiantech.internship.summer.service_broadcastreceiver.model.Units

class MusicAdapter(private val listMusic: ArrayList<MusicModel>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun getItemCount() = listMusic.size

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        (holder as? MusicViewHolder)?.bindData()
    }

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val musicName: TextView = itemView.findViewById(R.id.tvMusicTitle)
        private val musicDuration: TextView = itemView.findViewById(R.id.tvMusicTime)
        private val musicArtist: TextView = itemView.findViewById(R.id.tvMusicArtist)
        private val imgPlay: ImageView = itemView.findViewById(R.id.imgPlay)

        init{
            itemView.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData(){
            listMusic[adapterPosition].let{
                musicName.text = it.musicName
                musicDuration.text = Units.convertTimeMusic(it.musicDuration)
                musicArtist.text = it.musicArtist
                imgPlay.setImageURI(Uri.parse(it.musicImage))
            }
        }
    }
}
