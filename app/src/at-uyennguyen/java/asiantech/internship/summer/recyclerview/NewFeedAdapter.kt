package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.bumptech.glide.Glide

class NewFeedAdapter(val newFeed: MutableList<NewFeed>) : RecyclerView.Adapter<NewFeedAdapter.NewFeedViewHolder>() {
    internal var onItemClickedToLike: (position: Int) -> Unit = {}
    internal var onItemClickedToDelete: (position: Int) -> Unit = {}
    internal var onItemClickedToAdd: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFeedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.newfeed_item, parent, false)
        return NewFeedViewHolder(view)
    }

    override fun getItemCount() = newFeed.size

    override fun onBindViewHolder(holder: NewFeedViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class NewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        private var tvName: TextView = itemView.findViewById(R.id.tvName)
        private var imgPicture: ImageView = itemView.findViewById(R.id.imgPicture)
        private var imgHeart: ImageView = itemView.findViewById(R.id.imgHeart)
        private var tvNumberLike: TextView = itemView.findViewById(R.id.tvNumberLike)
        private var tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private var tvNameComment: TextView = itemView.findViewById(R.id.tvNameComment)
        private var imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        private var imgAdd: ImageView = itemView.findViewById(R.id.imgAdd)

        internal fun bindData(position: Int) {
            imgHeart.setOnClickListener { onItemClickedToLike.invoke(adapterPosition) }
            imgDelete.setOnClickListener { onItemClickedToDelete.invoke(adapterPosition) }
            imgAdd.setOnClickListener { onItemClickedToAdd.invoke(adapterPosition) }
            newFeed[position].run {
                tvName.text = name
                Glide.with(itemView)
                        .load(avatar)
                        .into(imgAvatar)
                Glide.with(itemView)
                        .load(picture)
                        .into(imgPicture)
                tvDescription.text = status
                tvNumberLike.text = numberLike.toString()
                tvNameComment.text = name
                imgHeart.setImageResource(if (!isLike) R.drawable.ic_heart_red else R.drawable.ic_heart_black)
            }
        }
    }
}
