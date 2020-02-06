package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class NewFeedAdapter(private val newFeeds: MutableList<NewFeed>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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
        private val tvFoodName : TextView = itemView.findViewById(R.id.tvFoodName)

        init {
            imgLike.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            val item = newFeeds[adapterPosition]
            tvUserName.text = item.name
            imgBackGround.setImageResource(item.picture)
            if (item.isStatus) imgLike.setImageResource(R.drawable.ic_like_red)
            else imgLike.setImageResource(R.drawable.ic_like)
            tvLikeNumber.text = item.likes.toString()
            tvFoodName.text = item.foodName
            tvPreview.text = item.preview
        }
    }

}
