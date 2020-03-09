package asiantech.internship.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.bumptech.glide.Glide

class TimeLines(private val timeLineViewHolders: List<TimeLineItem>) :
        RecyclerView.Adapter<TimeLines.TimeLineViewHolder>() {
    internal var onItemClicked: (position: Int) -> Unit = {}
    internal var onItemDeleteClick: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return TimeLineViewHolder(view)
    }

    override fun getItemCount() = timeLineViewHolders.size

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        (holder as? TimeLineViewHolder)?.bindData()
    }

    inner class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var tvNameTop: TextView = itemView.findViewById(R.id.tvNameTop)
        private val imgFood: ImageView = itemView.findViewById(R.id.imgFood)
        private val tgLike: ToggleButton = itemView.findViewById(R.id.tgLike)
        private val tvTotalLiked: TextView = itemView.findViewById(R.id.tvTotalLiked)
        private var tvNameBottom: TextView = itemView.findViewById(R.id.tvNameBottom)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        init {
            tgLike.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
            btnDelete.setOnClickListener {
                onItemDeleteClick.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            timeLineViewHolders[adapterPosition].let {
                tvNameTop.text = it.name
                Glide.with(itemView)
                        .load(it.imageFood)
                        .into(imgFood)
                tvNameBottom.text = it.name
                tvDescription.text = it.description
                tvTotalLiked.text = itemView.context.getString(R.string.tv_totalLike, it.like)
                tgLike.isChecked = it.isLike
            }
        }
    }
}
