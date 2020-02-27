package asiantech.internship.summer.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout
import kotlinx.android.synthetic.`at-cuongle`.row_todo.*

class ToDo(private val toDoViewHolder: MutableList<ToDoItems>) : RecyclerView.Adapter<ToDo.ListToDoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_todo, parent, false)
        return ListToDoViewHolder(view)
    }

    override fun getItemCount(): Int = toDoViewHolder.size

    override fun onBindViewHolder(holder: ListToDoViewHolder, position: Int) {
        (holder as? ListToDoViewHolder)?.bindData()
    }

    inner class ListToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRowToDo = itemView.findViewById<TextView>(R.id.tvRowToDo)
        private val swipe = itemView.findViewById<SwipeLayout>(R.id.swipe)
        internal fun bindData() {
            toDoViewHolder[adapterPosition].let {
                tvRowToDo.text = it.title
//                swipe.showMode = SwipeLayout.ShowMode.PullOut
//                swipe.addDrag(SwipeLayout.DragEdge.Left, swipe)
            }
        }
    }
}