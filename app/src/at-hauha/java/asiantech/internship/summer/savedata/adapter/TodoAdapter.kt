package asiantech.internship.summer.savedata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.Todo
import com.daimajia.swipe.SwipeLayout

class TodoAdapter(private val todoList: List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun getItemCount(): Int = todoList.size


    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        (holder as? TodoItemViewHolder)?.bindData()
    }

    internal var onItemClick: (position: Int) -> Unit = {}
    internal var onItemClickDelete: (position: Int) -> Unit = {}
    internal var onItemClickEdit: (position: Int) -> Unit = {}

    inner class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTodo: TextView = itemView.findViewById(R.id.tvTodo)
        private val sample: SwipeLayout = itemView.findViewById(R.id.swipeLayout)
        private val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
        private val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        private val chkTodo : CheckBox = itemView.findViewById(R.id.chkTodo)

        init {
            chkTodo.setOnCheckedChangeListener { _, _ ->
                onItemClick.invoke(adapterPosition)
            }
            imgEdit.setOnClickListener {
                onItemClickEdit.invoke(adapterPosition)
            }
            imgDelete.setOnClickListener {
                onItemClickDelete.invoke(adapterPosition)
            }
        }

        fun bindData() {
            todoList[adapterPosition].let {
                tvTodo.text = it.todo
                sample.showMode = SwipeLayout.ShowMode.LayDown
                if(it.isStatus){
                    chkTodo.visibility = View.INVISIBLE
                }
            }
        }
    }

}