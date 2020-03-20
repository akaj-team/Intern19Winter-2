package asiantech.internship.summer.apptodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class DoneTodoAdapter(val listTodo: ArrayList<Todo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_done_todo, parent, false)
        return DoneTodoViewHolder(view)
    }

    override fun getItemCount(): Int = listTodo.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DoneTodoViewHolder)?.bindData(position)
    }

    inner class DoneTodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTextDoneTodo: TextView = itemView.findViewById(R.id.tvDoneTodo)

        internal fun bindData(position: Int) {
            listTodo[position].run {
                tvTextDoneTodo.text = listTodo[position].textTodo
            }
        }
    }
}
