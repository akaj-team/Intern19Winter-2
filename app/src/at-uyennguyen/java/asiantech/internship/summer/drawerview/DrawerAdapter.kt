package asiantech.internship.summer.drawerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import de.hdodenhof.circleimageview.CircleImageView

class DrawerAdapter(val items: MutableList<DrawerItem?>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}
    private var selectedPosition: Int = 1

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header_drawer, parent, false)
            return DrawerItemHeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer, parent, false)
        return DrawerItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DrawerItemViewHolder)?.bindData()
        (holder as? DrawerItemHeaderViewHolder)
    }

    inner class DrawerItemHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val imgAvatar: CircleImageView = itemView.findViewById(R.id.imgAvatar)

        init {
            imgAvatar.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }
    }

    inner class DrawerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)

        init {
            itemView.setOnClickListener {
                selectedPosition = adapterPosition
                Toast.makeText(itemView.context, items[adapterPosition]?.name, Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }
        }

        fun bindData() {
            items[adapterPosition]?.run {
                if (adapterPosition == selectedPosition) {
                    imgIcon.setImageResource(iconcolor)
                    tvName.text = name
                    tvName.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange))
                } else {
                    imgIcon.setImageResource(icon)
                    tvName.text = name
                    tvName.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                }
            }
        }
    }
}
