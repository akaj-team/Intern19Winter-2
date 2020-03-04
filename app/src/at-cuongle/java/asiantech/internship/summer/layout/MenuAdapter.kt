package asiantech.internship.summer.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MenuAdapter(private val menu: MutableList<Menu?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MENU = 1
    }

    internal var onItemMenuClicked: (positon: Int) -> Unit = {}
    override fun getItemViewType(position: Int): Int {
        if (menu[position]?.icon == 0) {
            return TYPE_HEADER
        }
        return TYPE_MENU
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer_header, parent, false)
            return HeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int = menu.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? HeaderViewHolder)?.bindData()
        (holder as? MenuViewHolder)?.bindData()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar = itemView.findViewById<CircleImageView>(R.id.imgAvatar)
        internal fun bindData() {
            Glide.with(itemView).load(menu[adapterPosition]?.item).into(imgAvatar)
        }
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMenu = itemView.findViewById<TextView>(R.id.tvMenu)

        init {
            tvMenu.setOnClickListener {
                onItemMenuClicked.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            tvMenu.text = menu[adapterPosition]?.item
            menu[adapterPosition]?.icon?.let { tvMenu.setCompoundDrawablesWithIntrinsicBounds(it, 0, 0, 0) }
        }
    }
}
