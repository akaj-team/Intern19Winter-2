package asiantech.internship.summer.savedata.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.DrawerItem

class DrawerAdapter(private val drawerItem: MutableList<DrawerItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_ITEM = 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else
            TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            return DrawerHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_header, parent, false))
        }
        return DrawerItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun getItemCount() = drawerItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DrawerItemViewHolder)?.binData()
        (holder as? DrawerHeaderViewHolder)?.binData()
    }

    internal var onItemClicked: (position: Int) -> Unit = {}

    inner class DrawerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvItem: TextView = itemView.findViewById(R.id.tvItemUser)

        init {
            tvItem.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun binData() {
            drawerItem[adapterPosition].let {
                tvItem.text = it.action
                tvItem.setCompoundDrawablesWithIntrinsicBounds(it.icon.toInt(), 0, 0, 0)
            }
        }
    }

    inner class DrawerHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatarUser)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

        fun binData() {
            drawerItem[adapterPosition].let {
                if (it.icon.isNotEmpty()) {
                    imgAvatar.setImageURI(Uri.parse(it.icon))
                }else{
                    imgAvatar.setImageResource(R.drawable.ic_avatar)
                }
                tvEmail.text = it.action
            }
        }
    }

}