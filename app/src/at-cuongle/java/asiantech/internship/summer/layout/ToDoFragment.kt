package asiantech.internship.summer.layout

import android.os.Bundle
import android.text.InputType
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

class ToDoFragment : Fragment() {
    private var toDoAdapter: ToDoAdapter? = null
    private var position: Int = 0
    private var db: DataConnection? = null
    private var toDoList: MutableList<ToDoList>? = null
    private var toDo: ToDoList? = null
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
        toDoList = db?.toDoDao()?.getAllTaskStatusFalse()
        toDoAdapter = toDoList?.let { ToDoAdapter(it) }
        recyclerViewToDo.adapter = toDoAdapter
        toDoAdapter?.onItemEditClick = {
            showDialog(getString(R.string.dialog_title_edit))
            position = it
        }
        toDoAdapter?.onItemDeleteClick = {
            deleteToDo(it)
        }
        toDoAdapter?.onItemCheckBoxClick = {
            updateStatus(it)
        }
    }

    private fun initListener() {
        btnAddToDo.setOnClickListener {
            showDialog(getString(R.string.title_dialog_add_todo))
        }
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
                    if (editText.text.toString().isBlank()) {
                        Toast.makeText(context, "Please insert to do", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, getString(R.string.toast_added), Toast.LENGTH_SHORT).show()
                        addToDo(editText.text.toString())
                    }
                }
            } else {
                editText.setText(toDoList?.get(position)?.todoTitle)
                setPositiveButton(android.R.string.ok) { _, _ ->
                    Toast.makeText(context, getString(R.string.toast_edited), Toast.LENGTH_SHORT).show()
                    toDoList?.get(position)?.id?.let { editToDo(it, editText.text.toString()) }
                }
            }

            setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(context, R.string.toast_cancel, Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }

    private fun updateStatus(position: Int) {
        val id = toDoList?.get(position)?.todoTitle?.let { db?.toDoDao()?.findId(it) }
        id?.let { db?.toDoDao()?.updateStatus(it, true) }
        toDoList?.removeAt(position)
        toDoAdapter?.notifyDataSetChanged()
        Toast.makeText(context, "Done Task", Toast.LENGTH_LONG).show()
    }

    private fun addToDo(title: String) {
        toDo = ToDoList(todoTitle = title, isDone = false)
        toDo?.let { it -> db?.toDoDao()?.insertTask(it) }
        toDo?.let { toDoList?.add(it) }
        toDoAdapter?.notifyDataSetChanged()
    }

    private fun editToDo(id: Int, title: String) {
        db?.toDoDao()?.updateTask(id, title)
        toDoList?.get(position)?.todoTitle = title
        toDoAdapter?.notifyDataSetChanged()
    }

    private fun deleteToDo(id: Int) {
        toDoList?.get(id)?.let { db?.toDoDao()?.deleteTask(it) }
        toDoList?.removeAt(position)
        toDoAdapter?.notifyDataSetChanged()
    }
}
