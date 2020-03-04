package asiantech.internship.summer.apptodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_todo.*

class TodoFragment : Fragment() {
    private lateinit var databaseManager: DatabaseManager
    private var listTodoSQL = arrayListOf<Todo>()
    private var listTodo = arrayListOf<Todo>()
    private lateinit var adapterTodo: TodoAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { databaseManager = DatabaseManager(it) }
        listTodoSQL = databaseManager.getAllTodo()
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        adapterTodo = TodoAdapter(listTodo)
        adapterTodo.onItemClickedEdit = {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.frameLayoutTodo, EditTodoFragment.newInstance(listTodo[it]))
                    ?.addToBackStack(null)
                    ?.commit()
        }
        adapterTodo.onItemClickedDelete = {
            databaseManager.deleteTodo(listTodo[it].idTodo)
            listTodo.removeAt(it)
            adapterTodo.notifyDataSetChanged()
        }
        adapterTodo.onItemClickedDone = {
            databaseManager.deleteTodo(listTodo[it].idTodo)
            databaseManager.addDoneTodo(Todo(idTodo = 0, idUserTodo = listTodo[it].idUserTodo, textTodo = listTodo[it].textTodo))
            listTodo.removeAt(it)
            adapterTodo.notifyDataSetChanged()
        }
        recycleViewTodo.adapter = adapterTodo
    }

    private fun initData() {
        for (i in 0..listTodoSQL.size - 1) {
            listTodo.add(listTodoSQL[i])
        }
    }

}