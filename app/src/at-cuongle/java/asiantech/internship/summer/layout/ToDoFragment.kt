package asiantech.internship.summer.layout

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.DataConnection
import asiantech.internship.summer.layout.database.model.ToDoList
import kotlinx.android.synthetic.`at-cuongle`.fragment_to_do.*
import kotlinx.android.synthetic.`at-cuongle`.row_todo.*


class ToDoFragment : Fragment() {
    private lateinit var toDoAdapter: ToDoAdapter
    private var position: Int = 0
    var onOk: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    private var db: DataConnection? = null
    private var toDoList: ToDoList? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataConnection.connectData(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initListener()
    }

    private fun initAdapter() {
        toDoAdapter = db?.toDoDao()?.getAllTask()?.let { ToDoAdapter(it) }!!
        recyclerViewToDo.adapter = toDoAdapter
        toDoAdapter.onItemEditClick = {
            showDialog("Edit To Do")
            position = it
        }
        toDoAdapter.onItemDeleteClick = {
            deleteToDo(id = it + 1, title = "aaaa")
        }
        toDoAdapter.onItemCheckBoxClick = {
            Toast.makeText(context, chkIsDone.isChecked.toString(), Toast.LENGTH_LONG).show()
            if (chkIsDone.isChecked) {
//                val title = toDoItems[it].title
//                Log.i("XXX", title)
//                toDoItems[it].isDone = true
//                toDoItems.removeAt(it)
                toDoAdapter.notifyItemChanged(it)
            }
        }
    }

    private fun initListener() {
        btnAddToDo.setOnClickListener {
            showDialog("Add To Do")
        }
    }

    private fun addToDo(title: String) {
        toDoList = ToDoList(todoTitle = title, isDone = false)
        toDoList?.let { it -> db?.toDoDao()?.insertTask(it) }
        toDoAdapter.notifyDataSetChanged()
    }

    private fun editToDo(title: String, uid: Int) {
        toDoList = ToDoList(id = uid, todoTitle = title, isDone = false)
        toDoList?.let { it -> db?.toDoDao()?.updateTask(it) }
        toDoAdapter.notifyDataSetChanged()
    }

    private fun deleteToDo(title: String, id: Int) {
        toDoList = ToDoList(id = id, todoTitle = title, isDone = false)
        toDoList?.let { it -> db?.toDoDao()?.deleteTask(it) }
        toDoAdapter.notifyDataSetChanged()
    }

    private fun showDialog(action: String) {
        val editText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
        dialogOption.setView(editText)
        dialogOption.apply {
            setTitle(action)
            if (action == "Add To Do") {
                setPositiveButton(android.R.string.ok) { _, _ ->
                    onOk?.invoke()
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                    addToDo(editText.text.toString())
                }
            } else {
                setPositiveButton(android.R.string.ok) { _, _ ->
                    onOk?.invoke()
                    Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
                    editToDo(editText.text.toString(), position + 1)
                    Log.i("XXX", position.toString())
                }
            }

            setNegativeButton(android.R.string.cancel) { _, _ ->
                onCancel?.invoke()
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }
}
