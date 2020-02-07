package asiantech.internship.summer.drawerLayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class DrawerLayoutAdapter(private val drawerLayoutViewHolder: MutableList<Menu>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var onItemClicked: (position: Int) -> Unit = {}

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
            return InforViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_bottom, parent, false)
        return DrawerViewHolder(view)
    }

    override fun getItemCount() = drawerLayoutViewHolder.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DrawerViewHolder)?.bindData()
        (holder as? InforViewHolder)?.bindData()
    }

    inner class DrawerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        init {
            tvTitle.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            drawerLayoutViewHolder[adapterPosition].let {
                tvTitle.text = it.textTitle
            }
        }
    }

    inner class InforViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

        init {
            imgAvatar.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            tvEmail.text = itemView.context.getString(R.string.tv_email, null)
            imgAvatar.setImageResource(R.drawable.ic_avatar)
        }
    }
}
