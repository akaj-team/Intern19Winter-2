package asiantech.internship.summer.service_broadcastReceiver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class MusicAdapter(private val recyclerViewHolder: MutableList<Music>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var onSongClicked: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_music, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = recyclerViewHolder.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bindData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvNameMusic: TextView = itemView.findViewById(R.id.tvNameMusic)
        private var tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private var tvArtist: TextView = itemView.findViewById(R.id.tvArtist)
        private var imgMusic: ImageView = itemView.findViewById(R.id.imgMusic)

        init {
            itemView.setOnClickListener {
                onSongClicked.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            recyclerViewHolder[adapterPosition].let {
                tvNameMusic.text = it.name
                tvTime.text = MusicData.toMin(it.duration.toLong(), itemView.context)
                tvArtist.text = it.artist
                val bitmap = MusicData.convertUriToBitmap(it.uri, itemView.context)
                if (bitmap != null) {
                    imgMusic.setImageURI(it.image)
                } else {
                    imgMusic.setImageResource(R.drawable.ic_music)
                }
            }
        }
    }
}
