package asiantech.internship.summer.savedata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.ToDoModel

class ListToDoAdapter(private val listToDo: MutableList<ToDoModel>) : RecyclerView.Adapter<ListToDoAdapter.ListToDoViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ListToDoViewHolder(view)
    }

    override fun getItemCount() = listToDo.size

    override fun onBindViewHolder(holder: ListToDoViewHolder, position: Int) {
        (holder as? ListToDoViewHolder)?.bindData()
    }

    inner class ListToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvToDoName: TextView = itemView.findViewById(R.id.tvToDoName)
        private val cbStatus: CheckBox = itemView.findViewById(R.id.cbStatus)

        init {
            cbStatus.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            listToDo[adapterPosition].let {
                tvToDoName.text = it.toDoName
                cbStatus.isChecked = it.status == 0
            }
        }
    }
}
