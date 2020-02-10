package asiantech.internship.summer.drawerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import de.hdodenhof.circleimageview.CircleImageView

class ItemAdapter(val itemModel: MutableList<ItemModel?>, context: Context) :
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
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_header, parent, false)
            return HeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemModel.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.bindData(position)
        (holder as? HeaderViewHolder)?.bindData(position)
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val imgAvatar: CircleImageView = itemView.findViewById(R.id.imgAvatar)
        fun bindData(position: Int) {
            itemModel[position]?.run {
                tvEmail.text = email
            }
            imgAvatar.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        fun bindData(position: Int) {
            if (position == selectedPosition) {
                itemModel[position]?.run {
                    imgIcon.setImageResource(iconcolor)
                    tvName.text = name
                    tvName.setTextColor(Color.parseColor("#FF5252"))
                }
            } else {
                itemModel[position]?.run {
                    imgIcon.setImageResource(icon)
                    tvName.text = name
                    tvName.setTextColor(Color.parseColor("#0A0A0A"))
                }
            }
            itemView.setOnClickListener {
                selectedPosition = position
                Toast.makeText(itemView.context, itemModel[position]?.name, Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }
        }
    }
}
