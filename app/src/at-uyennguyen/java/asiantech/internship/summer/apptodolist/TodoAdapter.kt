package asiantech.internship.summer.apptodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class TodoAdapter(val listTodo: ArrayList<Todo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onItemClickedEdit: (position: Int) -> Unit = {}
    internal var onItemClickedDelete: (position: Int) -> Unit = {}
    internal var onItemClickedDone: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int = listTodo.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? TodoViewHolder)?.bindData(position)
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTextTodo: TextView = itemView.findViewById(R.id.tvTodo)
        private var imgEdit: ImageView = itemView.findViewById(R.id.imgEditTodo)
        private var imgDelete: ImageView = itemView.findViewById(R.id.imgTrushTodo)
        private var imgDone: ImageView = itemView.findViewById(R.id.imgDoneTodo)

        internal fun bindData(position: Int) {
            imgEdit.setOnClickListener { onItemClickedEdit.invoke(adapterPosition) }
            imgDelete.setOnClickListener { onItemClickedDelete.invoke(adapterPosition) }
            imgDone.setOnClickListener { onItemClickedDone.invoke(adapterPosition) }
            listTodo[position].run {
                tvTextTodo.text = listTodo[position].textTodo
            }
        }
    }
}
