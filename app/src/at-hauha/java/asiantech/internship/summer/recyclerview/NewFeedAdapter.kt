package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class NewFeedAdapter(private val newfeeds: MutableList<NewFeed>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (newfeeds[position].name.isBlank()) {
            return VIEW_TYPE_LOADING
        }
        return VIEW_TYPE_ITEM
    }

    private fun addLoadingView() {
        newfeeds.add(NewFeed("", R.drawable.ic_damson_crumble, false, 241, "This "))
        notifyItemChanged(newfeeds.size - 1)
    }

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bindData()
        (holder as? LoadingViewHolder)?.bindData()
    }

    override fun getItemCount(): Int {
        return newfeeds.size
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        fun bindData() {
        }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        private val imgBackGround: ImageView = itemView.findViewById(R.id.imgBackGround)
        private val tvLikeNumber: TextView = itemView.findViewById(R.id.tvLikeNumber)
        private val tvPreview: TextView = itemView.findViewById(R.id.tvPreview)
        private val imgLike: ImageView = itemView.findViewById(R.id.imgLike)

        init {
            imgLike.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            val item = newfeeds[adapterPosition]
            tvUserName.text = item.name
            imgBackGround.setImageResource(item.picture)
            if (item.isStatus) imgLike.setImageResource(R.drawable.ic_like_red)
            else imgLike.setImageResource(R.drawable.ic_like)
            tvLikeNumber.text = item.likes.toString()
            tvPreview.text = item.preview
        }
    }
}