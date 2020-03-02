package asiantech.internship.summer.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.model.ToDoList
import com.daimajia.swipe.SwipeLayout

class ToDoAdapter(private val toDo: MutableList<ToDoList>) : RecyclerView.Adapter<ToDoAdapter.ListToDoViewHolder>() {
    internal var onItemEditClick: (position: Int) -> Unit = {}
    internal var onItemDeleteClick: (position: Int) -> Unit = {}
    internal var onItemCheckBoxClick: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_todo, parent, false)
        return ListToDoViewHolder(view)
    }

    override fun getItemCount(): Int = toDo.size

    override fun onBindViewHolder(holder: ListToDoViewHolder, position: Int) {
        (holder as? ListToDoViewHolder)?.bindData()
    }

    inner class ListToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRowToDo = itemView.findViewById<TextView>(R.id.tvRowToDo)
        private val swipe = itemView.findViewById<SwipeLayout>(R.id.swipe)
        private val btnDelete = itemView.findViewById<Button>(R.id.btnDelete)
        private val btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
        private val chkIsDone = itemView.findViewById<CheckBox>(R.id.chkIsDone)

        init {
            btnDelete.setOnClickListener {
                onItemDeleteClick.invoke(adapterPosition)
            }
            btnEdit.setOnClickListener {
                onItemEditClick.invoke(adapterPosition)
            }
            chkIsDone.setOnCheckedChangeListener { buttomView, isChecked ->
                onItemCheckBoxClick.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            toDo[adapterPosition].let {
                tvRowToDo.text = it.todoTitle
                swipe.showMode = SwipeLayout.ShowMode.LayDown
            }
        }
    }
}