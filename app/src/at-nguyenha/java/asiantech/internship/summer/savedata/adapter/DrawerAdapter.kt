package asiantech.internship.summer.savedata.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.MenuModel
import com.bumptech.glide.Glide


class DrawerAdapter(private val listMenu: MutableList<MenuModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MENU = 1
    }

    internal var onClickEditProfile: (position: Int) -> Unit = {}
    internal var onClickAddTodo: (position: Int) -> Unit = {}
    internal var onClickLogout: (position: Int) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_MENU
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer_menu, parent, false)
                MenuViewHolder(view)
            }
        }
    }

    override fun getItemCount() = listMenu.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? HeaderViewHolder)?.bindData()
        (holder as? MenuViewHolder)?.bindData()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar = itemView.findViewById<ImageView>(R.id.imgDrawerAvatar)
        private val tvName = itemView.findViewById<TextView>(R.id.tvDrawerName)

        fun bindData() {
            listMenu[adapterPosition].let {
                Glide.with(itemView)
                        .load(Uri.parse(it.image))
                        .placeholder(R.drawable.ic_account)
                        .into(imgAvatar)
                tvName.text = it.text
            }
        }
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgMenu = itemView.findViewById<ImageView>(R.id.imgDrawerMenu)
        private val tvMenu = itemView.findViewById<TextView>(R.id.tvDrawerMenu)

        init {
            itemView.setOnClickListener {
                when (adapterPosition) {
                    1 -> {
                        onClickEditProfile.invoke(adapterPosition)
                    }
                    2 -> {
                        onClickAddTodo.invoke(adapterPosition)
                    }
                    3 -> {
                        onClickLogout.invoke(adapterPosition)
                    }
                }
            }
        }

        fun bindData() {
            listMenu[adapterPosition].let {
                tvMenu.text = it.text
                when (adapterPosition) {
                    1 -> Glide.with(itemView)
                            .load("")
                            .placeholder(R.drawable.ic_edit_black_24dp)
                            .into(imgMenu)
                    2 -> Glide.with(itemView)
                            .load("")
                            .placeholder(R.drawable.ic_lock)
                            .into(imgMenu)
                    else -> Glide.with(itemView)
                            .load("")
                            .placeholder(R.drawable.ic_logout)
                            .into(imgMenu)
                }
            }
        }
    }
}

