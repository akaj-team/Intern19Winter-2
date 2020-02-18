package asiantech.internship.summer.appmusic

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class ListMusicAdapter(val listMedia: MutableList<Media>) : RecyclerView.Adapter<ListMusicAdapter.SongListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_media, parent, false)
        return SongListViewHolder(view)
    }

    override fun getItemCount() = listMedia.size

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class SongListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)
        private var tvSongName: TextView = itemView.findViewById(R.id.tv_name)
        private var tvSinger: TextView = itemView.findViewById(R.id.tv_singer)
        private var tvTime: TextView = itemView.findViewById(R.id.tv_time)
        internal fun bindData(position: Int) {
            listMedia[position].run {
                tvSongName.setText(nameSong)
                tvSinger.setText(singer)
                tvTime.setText(time)
                imgThumbnail.setImageURI(thumbnail)
            }
        }
        init {
            itemView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {

                }
            })
        }
    }
}