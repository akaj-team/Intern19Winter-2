package asiantech.internship.summer.savedata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return TodoItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }


    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        (holder as? TodoItemViewHolder )?.bindData()
    }

    internal var onItemClick: (position: Int  ) -> Unit = {}

    inner class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val cardView : CardView = itemView.findViewById(R.id.cardView)
        init {
            cardView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bindData(){

        }
    }

}