package asiantech.internship.summer.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.bumptech.glide.Glide

class NewFeedAdapter(private val newFeeds: MutableList<NewFeedModel>) : RecyclerView.Adapter<NewFeedAdapter.NewFeedViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newfeed, parent, false)
        return NewFeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newFeeds.size
    }

    override fun onBindViewHolder(holder: NewFeedViewHolder, position: Int) {
        (holder as? NewFeedViewHolder)?.binData()
    }

    inner class NewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val imgMain: ImageView = itemView.findViewById(R.id.imgMainPhoto)
        private val imgHeart: ImageView = itemView.findViewById(R.id.imgHeart)
        private val tvLikes: TextView = itemView.findViewById(R.id.tvHeartNumber)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvNameStatus: TextView = itemView.findViewById(R.id.tvNameStatus)
        private val tvFoodName: TextView = itemView.findViewById(R.id.tvFoodName)


        init {
            imgHeart.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        internal fun binData() {
            newFeeds[adapterPosition].let {
                tvName.text = it.name
                Glide.with(itemView)
                        .load(it.mainImage)
                        .placeholder(R.drawable.ic_empty_image)
                        .into(imgMain)
                if (it.isHeart) imgHeart.setImageResource(R.drawable.ic_hearted) else imgHeart.setImageResource(R.drawable.ic_heart)
                tvNameStatus.text = it.name
                tvFoodName.text = it.foodName
                tvLikes.text = itemView.context.getString(R.string.textview_text_like_number, it.likeNumber)
                tvStatus.text = it.status
            }
        }
    }
}
