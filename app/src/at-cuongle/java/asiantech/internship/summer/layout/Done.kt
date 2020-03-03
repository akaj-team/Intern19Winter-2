package asiantech.internship.summer.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.model.ToDoList

class Done(private val doneViewHolder: MutableList<ToDoList>) : RecyclerView.Adapter<Done.ListDoneViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDoneViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_done, parent, false)
        return ListDoneViewHolder(view)
    }

    override fun getItemCount(): Int = doneViewHolder.size

    override fun onBindViewHolder(holder: ListDoneViewHolder, position: Int) {
        (holder as? ListDoneViewHolder)?.bindData()
    }

    inner class ListDoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRowDone = itemView.findViewById<TextView>(R.id.tvRowDone)
        internal fun bindData() {
            doneViewHolder[adapterPosition].let {
                tvRowDone.text = it.todoTitle
            }
        }
    }
}