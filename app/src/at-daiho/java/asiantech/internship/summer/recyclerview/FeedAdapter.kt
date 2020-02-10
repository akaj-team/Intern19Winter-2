package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class FeedAdapter(private val feeds: MutableList<Feed>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bindData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOwnerUsername: TextView = itemView.findViewById(R.id.tvOwnerUsername)
        private val imgFeed: ImageView = itemView.findViewById(R.id.imgFeed)
        private val tvNumberOfLike: TextView = itemView.findViewById(R.id.tvNumberOfLike)
        private val tvCommentOwner: TextView = itemView.findViewById(R.id.tvCommentOwner)
        private val tvComment: TextView = itemView.findViewById(R.id.tvComment)
        private val imgLike: ImageView = itemView.findViewById(R.id.imgBtnLike)

        init {
            imgLike.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            val item = feeds[adapterPosition]
            tvOwnerUsername.text = item.name
            imgFeed.setImageResource(item.pictureIndex)
            if (item.noOfLikes == 1)
                tvNumberOfLike.text = String.format(itemView.context.getString(R.string.feed_item_number_of_like, item.noOfLikes))
            else
                tvNumberOfLike.text = String.format(itemView.context.getString(R.string.feed_item_number_of_likes, item.noOfLikes))
            tvCommentOwner.text = item.commentOwner
            tvComment.text = item.comment
            if (item.isLike)
                imgLike.setImageResource(R.drawable.ic_like_red)
            else
                imgLike.setImageResource(R.drawable.ic_like)
        }
    }
}