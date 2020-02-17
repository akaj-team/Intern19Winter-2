package asiantech.internship.summer.layout


import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.`at-cuongle`.fragment_to_do.*

class ToDoFragment : Fragment() {
    private var toDoItems = mutableListOf<ToDoItems>()
    private lateinit var toDoAdapter: ToDo
    var onOk: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_to_do, container, false)
        val rvToDo = view.findViewById<RecyclerView>(R.id.rvToDo)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("XXX", toDoItems.size.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initListener()
        initData()
    }

    private fun initData() {
        toDoItems.apply {
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
            add(ToDoItems("Eat"))
        }
        toDoAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        toDoAdapter = ToDo(toDoItems)
        rvToDo.adapter = toDoAdapter
    }

    private fun initListener() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    val deletedModel = toDoItems[position]
                    toDoAdapter.notifyItemRemoved(position)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvToDo)
        btnAddToDo.setOnClickListener {
            showDialog()
        }
    }

    private fun addItem(title: String) {
        toDoItems.add(ToDoItems(title))
//        toDoAdapter.notifyItemInserted(toDoItems.size -1)
        toDoAdapter.notifyDataSetChanged()
    }

    private fun showDialog() {
        val editText: EditText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
        dialogOption.setView(editText)
        dialogOption.apply {
            setTitle("Add To Do")
            setPositiveButton(android.R.string.ok){_,_->
                onOk?.invoke()
                Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show()
                addItem(editText.text.toString())
            }
            setNegativeButton(android.R.string.cancel){_,_->
                onCancel?.invoke()
                Toast.makeText(context,"Cancel",Toast.LENGTH_SHORT).show()

            }
//            val dialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
//            setItems(dialogItems
//            ) { _, which ->
//                when (which) {
//                }
//            }
            show()
        }
    }
}