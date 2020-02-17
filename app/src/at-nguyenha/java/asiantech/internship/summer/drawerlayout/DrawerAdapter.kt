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

class DrawerAdapter(private val items: MutableList<ItemModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MENU = 1
    }

    private var positionSelected = 1
    internal var onItemClick: (position: Int) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else TYPE_MENU
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_drawer_header, parent, false)
            return DrawerItemHeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer, parent, false)
        return DrawerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DrawerItemViewHolder)?.bindData()
        (holder as? DrawerItemHeaderViewHolder)?.bindData()
    }

    inner class DrawerItemHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    inner class DrawerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemsView: View = itemView.findViewById(R.id.menu_item)
        private val imgIcon: ImageView = itemView.findViewById(R.id.imgMenu)
        private val tvItem: TextView = itemView.findViewById(R.id.tvMenu)

        init {
            itemsView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            items[adapterPosition].let {
                imgIcon.setImageResource(it.image)
                tvItem.text = it.textMenu
            }
            if (adapterPosition == positionSelected) {
                items[adapterPosition].let {
                    imgIcon.setColorFilter(Color.BLUE)
                    tvItem.setTextColor(Color.BLUE)
                }
            } else {
                items[adapterPosition].let {
                    imgIcon.setColorFilter(Color.BLACK)
                    tvItem.setTextColor(Color.BLACK)
                }
            }
            itemsView.setOnClickListener {
                positionSelected = adapterPosition
                Toast.makeText(itemsView.context, "Selected\t" + tvItem.text, Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }
        }
    }
}
