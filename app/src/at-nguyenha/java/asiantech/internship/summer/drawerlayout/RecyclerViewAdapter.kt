package asiantech.internship.summer.drawerlayout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class RecyclerViewAdapter(private val items: MutableList<ItemModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MENU = 1
    }

    private var positionSelected = 1
    internal var onItemClick: (position: Int) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_MENU
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            return HeaderItemViewHolder(view)
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MenuViewHolder)?.bindData(position)
        (holder as? HeaderItemViewHolder)?.bindData()
    }

    inner class HeaderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        private val tvEmail: TextView = itemView.findViewById((R.id.tvEmail))

        init {
            imgAvatar.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bindData() {
            items[adapterPosition].let {
                tvEmail.text = it.email
            }
        }
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemsView: View = itemView.findViewById(R.id.menu_item)
        private val imgIcon: ImageView = itemView.findViewById(R.id.imgMenu)
        private val tvItem: TextView = itemView.findViewById(R.id.tvMenu)

        init {
            itemsView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        internal fun bindData(position: Int) {
            var oldPosition : Int = 1
            items[adapterPosition].let {
                imgIcon.setImageResource(it.image)
                tvItem.text = it.textMenu
            }
            if (position == positionSelected) {
                items[adapterPosition].let {
                    imgIcon.setColorFilter(Color.BLUE)
                    tvItem.setTextColor(Color.BLUE)
                    oldPosition = position
                }
            } else {
                items[adapterPosition].let {
                    imgIcon.setColorFilter(Color.BLACK)
                    tvItem.setTextColor(Color.BLACK)
                }
            }
            itemsView.setOnClickListener {
                positionSelected = position
                Toast.makeText(itemsView.context, "Selected\t" + tvItem.text, Toast.LENGTH_LONG).show()
                notifyItemChanged(position)

            }

        }
    }
}
