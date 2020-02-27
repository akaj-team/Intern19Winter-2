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
import com.daimajia.swipe.SwipeLayout
import kotlinx.android.synthetic.`at-cuongle`.fragment_to_do.*
import kotlinx.android.synthetic.`at-cuongle`.row_todo.*

class ToDoFragment : Fragment() {
    private var toDoItems = mutableListOf<ToDoItems>()
    private lateinit var toDoAdapter: ToDo
    var onOk: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    private lateinit var swipeLayout: SwipeLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_to_do, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        btnAddToDo.setOnClickListener {
            showDialog()
        }
    }

    private fun addItem(title: String) {
        toDoItems.add(ToDoItems(title))
        toDoAdapter.notifyDataSetChanged()
    }

    private fun showDialog() {
        val editText: EditText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
        dialogOption.setView(editText)
        dialogOption.apply {
            setTitle("Add To Do")
            setPositiveButton(android.R.string.ok) { _, _ ->
                onOk?.invoke()
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                addItem(editText.text.toString())
            }
            setNegativeButton(android.R.string.cancel) { _, _ ->
                onCancel?.invoke()
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()

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