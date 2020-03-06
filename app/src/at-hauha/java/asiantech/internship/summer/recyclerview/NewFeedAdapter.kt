package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.bumptech.glide.Glide

class NewFeedAdapter(posts: List<NewFeed>) : RecyclerView.Adapter<NewFeedAdapter.NewFeedItemViewHolder>() {

    val newFeeds: List<NewFeed> = posts

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFeedItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return NewFeedItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewFeedItemViewHolder, position: Int) {
        (holder as? NewFeedItemViewHolder)?.bindData()
    }

    override fun getItemCount(): Int {
        return newFeeds.size
    }

    internal var onItemClicked: (position: Int) -> Unit = {}

    inner class NewFeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        private val imgBackGround: ImageView = itemView.findViewById(R.id.imgBackGround)
        private val tvLikeNumber: TextView = itemView.findViewById(R.id.tvLikeNumber)
        private val tvPreview: TextView = itemView.findViewById(R.id.tvPreview)
        private val imgLike: ImageView = itemView.findViewById(R.id.imgLike)
        private val tvFoodName: TextView = itemView.findViewById(R.id.tvFoodName)

        init {
            imgLike.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            newFeeds[adapterPosition].run {
                tvUserName.text = name
                Glide.with(itemView).load(picture).into(imgBackGround)
                if (isStatus) imgLike.setImageResource(R.drawable.ic_favorite_red_a700_24dp)
                else imgLike.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                tvLikeNumber.text = itemView.context.getString(R.string.textview_like, like)
                tvFoodName.text = foodName
                tvPreview.text = preview
            }
        }
    }

}

