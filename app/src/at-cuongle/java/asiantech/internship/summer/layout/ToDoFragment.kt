package asiantech.internship.summer.layout

import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.DataConnection
import asiantech.internship.summer.layout.database.model.ToDoList
import asiantech.internship.summer.layout.database.model.User
import kotlinx.android.synthetic.`at-cuongle`.fragment_to_do.*

class ToDoFragment : Fragment() {
    companion object {
        private const val ARG_USER_EMAIL = "userEmail"
        private const val ARG_PREFERENCES = "MyPref"
        private const val DEFAULT_VALUE = ""
        private const val OFFSET = 10
        private const val DELAY_TIME: Long = 2000
    }

    private var toDoAdapter: ToDoAdapter? = null
    private var db: DataConnection? = null
    private var toDoList: MutableList<ToDoList>? = null
    private var toDoListMore: MutableList<ToDoList>? = null
    private var toDo: ToDoList? = null
    private var user: User? = null
    private var position: Int = 0
    private var isLoading = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataConnection.connectData(requireContext())
        user = db?.userDao()?.findByEmail(getUserName())
        
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initListener()
    }

    private fun initAdapter() {
        toDoList = user?.uid?.let { db?.toDoDao()?.getAllTaskStatusFalse(it) }
        toDoAdapter = toDoList?.let { ToDoAdapter(it) }
        recyclerViewToDo.adapter = toDoAdapter
        toDoAdapter?.onItemEditClick = {
            position = it
            showDialog(getString(R.string.dialog_title_edit))
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
            showDialog(getString(R.string.dialog_title_add_todo))
        }
        recyclerViewToDo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == (toDoList?.size?.minus(1))) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            loadMore(lastVisibleItem)
                        }, DELAY_TIME)
                    }
                }
                isLoading = false
            }
        })
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
                        Toast.makeText(context, getString(R.string.toast_please_insert), Toast.LENGTH_SHORT).show()
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
        Toast.makeText(context, getString(R.string.toast_done), Toast.LENGTH_LONG).show()
    }

    private fun addToDo(title: String) {
        toDo = user?.uid?.let { ToDoList(todoTitle = title, isDone = false, uid = it) }
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
        toDoList?.get(id)?.todoTitle?.let { db?.toDoDao()?.deleteTask(it) }
        toDoList?.removeAt(id)
        toDoAdapter?.notifyDataSetChanged()
    }

    private fun getUserName(): String {
        val sharedPreferences = requireContext().getSharedPreferences(ARG_PREFERENCES, 0)
        return sharedPreferences?.getString(ARG_USER_EMAIL, DEFAULT_VALUE) ?: DEFAULT_VALUE
    }

    private fun loadMore(lastVisibleItem: Int) {
        toDoListMore = user?.uid?.let { db?.toDoDao()?.selectOffset(it, lastVisibleItem, OFFSET) }
        toDoListMore?.let { toDoList?.addAll(it) }
        toDoAdapter?.notifyDataSetChanged()
        progressBar.visibility = View.INVISIBLE
    }
}
