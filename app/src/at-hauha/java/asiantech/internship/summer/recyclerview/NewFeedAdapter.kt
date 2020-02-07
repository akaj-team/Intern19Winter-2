package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class NewFeedAdapter(private val newFeeds: MutableList<NewFeed>) : RecyclerView.Adapter<NewFeedAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bindData()
    }

    override fun getItemCount(): Int {
        return newFeeds.size
    }

    internal var onItemClicked: (position: Int) -> Unit = {}

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                imgBackGround.setImageResource(picture)
                if (isStatus) imgLike.setImageResource(R.drawable.ic_like_red)
                else imgLike.setImageResource(R.drawable.ic_like)
                tvLikeNumber.text = itemView.context.getString(R.string.textview_like,likes)
                tvFoodName.text = foodName
                tvPreview.text = preview
            }
        }
    }

}
