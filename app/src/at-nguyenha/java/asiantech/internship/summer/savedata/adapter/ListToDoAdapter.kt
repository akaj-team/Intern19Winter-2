package asiantech.internship.summer.savedata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.ToDoModel

class ListToDoAdapter(private val listToDo: MutableList<ToDoModel>) : RecyclerView.Adapter<ListToDoAdapter.ListToDoViewHolder>() {

    internal var onItemCheckBoxClicked: (position: Int) -> Unit = {}
    internal var onItemEditClicked: (position: Int) -> Unit = {}
    internal var onItemDeleteClicked: (position: Int) -> Unit = {}

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
        private val imgEdit : ImageView = itemView.findViewById(R.id.imgEditItem)
        private val imgDelete : ImageView = itemView.findViewById(R.id.imgDeleteItem)
        private val cardAction : CardView = itemView.findViewById(R.id.cardAction)

        init {
            cbStatus.setOnClickListener {
                onItemCheckBoxClicked.invoke(adapterPosition)
            }
            imgEdit.setOnClickListener {
                onItemEditClicked.invoke(adapterPosition)
            }
            imgDelete.setOnClickListener {
                onItemDeleteClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            listToDo[adapterPosition].let {
                tvToDoName.text = it.toDoName
                if (cbStatus.isChecked){
                    cardAction.visibility
                    cbStatus.visibility
                }
            }
        }
    }
}
