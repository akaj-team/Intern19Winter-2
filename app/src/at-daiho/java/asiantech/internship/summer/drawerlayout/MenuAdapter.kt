package asiantech.internship.summer.drawerlayout

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

class MenuAdapter(val menus: MutableList<Menu>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}
    private var selectedPosition: Int = 0
    private var user = User("dai.ho@asiantech.vn", "")

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MENU = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_MENU
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item_header, parent, false)
            return MenuHeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MenuViewHolder)?.bindData(position)
        (holder as? MenuHeaderViewHolder)?.bindData()
    }

    inner class MenuHeaderViewHolder(headerView: View) : RecyclerView.ViewHolder(headerView) {
        private val tvEmail: TextView = headerView.findViewById(R.id.tvEmail)
        private val imgAvatar: CircleImageView = headerView.findViewById(R.id.imgAvatar)
        fun bindData() {
            tvEmail.text = user.email
            imgAvatar.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }
    }

    inner class MenuViewHolder(menuView: View) : RecyclerView.ViewHolder(menuView) {
        private val imgIcon: ImageView = menuView.findViewById(R.id.imgIcon)
        private val tvTitle: TextView = menuView.findViewById(R.id.tvTitle)
        fun bindData(position: Int) {
            val convertPosition = position - 1
            if (convertPosition == selectedPosition) {
                menus[convertPosition].run {
                    imgIcon.setImageResource(selectedIcon)
                    tvTitle.text = title
                    tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_menu_title_selected))
                }
            } else {
                menus[convertPosition].run {
                    imgIcon.setImageResource(unSelectedIcon)
                    tvTitle.text = title
                    tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_menu_title_normal))
                }
            }
            itemView.setOnClickListener {
                selectedPosition = convertPosition
                Toast.makeText(itemView.context, menus[convertPosition].title, Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }
        }
    }
}
