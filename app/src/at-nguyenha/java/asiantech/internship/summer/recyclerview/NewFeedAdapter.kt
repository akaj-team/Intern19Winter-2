package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class NewFeedAdapter(private val newfeeds: MutableList<NewFeedModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newfeed, parent, false)
        return NewFeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newfeeds.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? NewFeedViewHolder)?.binData()
    }

    inner class NewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val imgMainphoto: ImageView = itemView.findViewById(R.id.imgMainphoto)
        private val imgHeart: ImageView = itemView.findViewById(R.id.imgHeart)
        private val tvLikes: TextView = itemView.findViewById(R.id.tvHeartnumber)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvNameStatus: TextView = itemView.findViewById(R.id.tvNamestatus)

        init {
            imgHeart.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun binData() {
            val newfeeds = newfeeds[adapterPosition]
            tvName.text = newfeeds.name
            imgMainphoto.setImageResource(newfeeds.mainimage)
            if (newfeeds.isHeart) imgHeart.setImageResource(R.drawable.ic_hearted) else imgHeart.setImageResource(R.drawable.ic_heart)
            tvNameStatus.text = newfeeds.name
            tvLikes.text = newfeeds.likeNumber.toString()
            tvStatus.text = newfeeds.status

        }
    }

}