package asiantech.internship.summer.drawerlayout

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class DrawerAdapter(private val drawerItemItem: MutableList<DrawerItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_ITEM = 0
    }

    private var positionSelected = 1

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer_header, parent, false)
            return DrawerItemHeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer, parent, false)
        return DrawerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DrawerItemViewHolder)?.binData()
        (holder as? DrawerItemHeaderViewHolder)?.binData()
    }

    override fun getItemCount(): Int {
        return drawerItemItem.size
    }

    inner class DrawerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMenu: TextView = itemView.findViewById(R.id.tvMenu)

        init {
            tvMenu.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
                positionSelected = adapterPosition
                notifyDataSetChanged()
            }
        }

        fun binData() {
            drawerItemItem.run {
                drawerItemItem[adapterPosition].let {
                    tvMenu.isSelected = adapterPosition == positionSelected
                    tvMenu.text = it.name
                    tvMenu.setCompoundDrawablesWithIntrinsicBounds(it.icon, 0, 0, 0)
                }
            }
        }
    }

    inner class DrawerItemHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)

        init {
            imgAvatar.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun binData() {
            val menuItem = drawerItemItem[adapterPosition]
            if (!menuItem.name.isBlank()) {
                imgAvatar.setImageURI(Uri.parse(menuItem.name))
            }
        }
    }

}
