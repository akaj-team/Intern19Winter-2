package asiantech.internship.summer.savedata

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.TodoAdapter
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.Todo
import kotlinx.android.synthetic.`at-hauha`.fragment_todo.*
import kotlinx.android.synthetic.`at-hauha`.todo_item.*


class TodoFragment : Fragment() {

    companion object {
        private const val ARG_ID = "userId"
        private const val ARG_STATUS = "isStatus"
        fun newInstance(userId: Int, isStatus: Boolean) = TodoFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, userId)
                putBoolean(ARG_STATUS, isStatus)
            }
        }
    }

    private var adapter: TodoAdapter? = null
    private var todoList: MutableList<Todo>? = null
    private var db: AppDatabase? = null
    private var userId = 0
    private var isStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            userId = getInt(ARG_ID)
            isStatus = getBoolean(ARG_STATUS)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.connectDatabase(requireContext())
        initAdapter()
    }

    private fun initAdapter() {
        todoList = db?.todoDao()?.selectAllByStatus(isStatus,userId)
        adapter = todoList?.let { TodoAdapter(it) }
        adapter?.onItemClick = {
            if (chkTodo.isChecked) {
                todoList?.get(it)?.id?.let { it1 -> db?.todoDao()?.updateStatus(true, it1) }
                todoList?.get(it)?.isStatus  = true
                adapter?.notifyDataSetChanged()
            }
        }
        adapter?.onItemClickEdit = {
            showDialog(it)
        }
        adapter?.onItemClickDelete = { it ->
            Toast.makeText(requireContext(), "Delete", Toast.LENGTH_LONG).show()
            todoList?.get(it)?.let { it1 -> db?.todoDao()?.deleteTodo(it1)}
            todoList?.removeAt(it)
            adapter?.notifyDataSetChanged()
        }
        recycle_view.layoutManager = LinearLayoutManager(requireContext())
        recycle_view.adapter = adapter
    }

    private fun showDialog(index: Int) {
        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
        val editText = EditText(context)
        editText.text = Editable.Factory.getInstance().newEditable(todoList?.get(index)?.todo)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        dialogOption.apply {
            setTitle("Edit todo")
            dialogOption.setView(editText)
            setPositiveButton(android.R.string.ok) { _, _ ->
                todoList?.get(index)?.id?.let { db?.todoDao()?.updateTodo(editText.text.toString(), it) }
                todoList?.get(index)?.todo = editText.text.toString()
                adapter?.notifyDataSetChanged()
                Toast.makeText(context, editText.text.toString(), Toast.LENGTH_SHORT).show()
            }
            setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(context, "CANCEL", Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }

}
