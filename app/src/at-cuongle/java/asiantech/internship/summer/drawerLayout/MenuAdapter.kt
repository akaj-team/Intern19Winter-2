package asiantech.internship.summer.drawerLayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.row_bottom.view.*

class MenuAdapter(private val drawerLayoutViewHolder: MutableList<Menu>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var onItemClicked: (position: Int) -> Unit = {}
    internal var onAvatarClick: () -> Unit = {}
    private var selectedItem = 0

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_ITEM = 0
    }

    override fun getItemViewType(position: Int): Int {
        if (drawerLayoutViewHolder[position].textTitle.isBlank()) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_top, parent, false)
            return HeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_bottom, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = drawerLayoutViewHolder.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.bindData()
        (holder as? HeaderViewHolder)?.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        init {
            tvTitle.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
                selectedItem = adapterPosition
                notifyDataSetChanged()
            }
        }

        internal fun bindData() {
            itemView.run {
                drawerLayoutViewHolder[adapterPosition].let {
                    tvTitle.text = it.textTitle
                    imgItem.setImageResource(it.icon)
                    imgItem.isSelected = selectedItem == adapterPosition
                    constraintLayout.isSelected = selectedItem == adapterPosition
                    tvTitle.isSelected = selectedItem == adapterPosition
                }
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

        init {
            imgAvatar.setOnClickListener {
                onAvatarClick.invoke()
            }
        }

        internal fun bindData() {
            tvEmail.text = itemView.context.getString(R.string.tv_email, null)
        }
    }
}
