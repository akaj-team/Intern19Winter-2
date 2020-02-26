package asiantech.internship.summer.appmusic

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class ListMusicAdapter(val listMedia: ArrayList<Media>, val context: Context) : RecyclerView.Adapter<ListMusicAdapter.SongListViewHolder>() {

    private var playMusicFragment = PlayMusicFragment()
    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list_media, parent, false)
        return SongListViewHolder(view)
    }

    override fun getItemCount() = listMedia.size

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class SongListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)
        private var tvSongName: TextView = itemView.findViewById(R.id.tvName)
        private var tvSinger: TextView = itemView.findViewById(R.id.tvSinger)
        private var tvTime: TextView = itemView.findViewById(R.id.tvCurrentPosition)
        internal fun bindData(position: Int) {
            listMedia[position].run {
                tvSongName.setText(nameSong)
                tvSinger.setText(singer)
                val time = playMusicFragment.convertDuration(time.toLong())
                tvTime.setText(time)
                imgThumbnail.setImageURI(Uri.parse(thumbnail))
                if (imgThumbnail.drawable == null) {
                    imgThumbnail.setImageResource(R.drawable.ic_music_note_white_36dp)
                }
            }
        }
        init {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onItemClicked.invoke(adapterPosition)
                }
            })
        }
    }
}
