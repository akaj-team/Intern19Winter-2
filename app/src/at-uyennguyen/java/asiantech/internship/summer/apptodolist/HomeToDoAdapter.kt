package asiantech.internship.summer.apptodolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class HomeToDoAdapter(val items: ArrayList<DrawerItem>, var user: User, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val USER = "user"
        fun newInstance(user: User): LoginToDoFragment {
            val loginToDoFragment = LoginToDoFragment()
            val bundle = Bundle()
            bundle.putParcelable(USER, user)
            loginToDoFragment.arguments = bundle
            return loginToDoFragment
        }
    }

    internal var onItemClicked: (position: Int) -> Unit = {}
    private var selectedPosition = 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        } else {
            return TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header_profile, parent, false)
            return DrawerItemHeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return DrawerItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DrawerItemHeaderViewHolder)?.bindData()
        (holder as? DrawerItemViewHolder)?.bindData()
    }

    inner class DrawerItemHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgAvatar: CircleImageView = itemView.findViewById(R.id.imgAvatarProfile)
        private var tvName: TextView = itemView.findViewById(R.id.tvUserName)
        private var tvNickname: TextView = itemView.findViewById(R.id.tvNicknameHome)
        fun bindData() {
            Glide.with(itemView)
                    .load(user.avatar)
                    .into(imgAvatar)
            tvName.text = user.nameUser
            tvNickname.text = user.nickName
        }
    }

    inner class DrawerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        private var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }

        fun bindData() {
            items[adapterPosition].run {
                if (adapterPosition == selectedPosition) {
                    imgIcon.setImageResource(iconColor)
                    tvTitle.text = title
                    tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange))
                } else {
                    imgIcon.setImageResource(icon)
                    tvTitle.text = title
                    tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                }
            }
        }
    }
}
