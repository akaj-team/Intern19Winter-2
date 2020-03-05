package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_todo.*

class TodoFragment : Fragment() {

    companion object {
        private const val ID_DEFAULT = -1
        private const val SHARED_ID = "id"
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
    }

    private lateinit var databaseManager: DatabaseManager
    private var sharedPreferences: SharedPreferences? = null
    private var listTodoSQL = arrayListOf<Todo>()
    private var listTodo = arrayListOf<Todo>()
    private lateinit var adapterTodo: TodoAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        val idDefault = sharedPreferences?.getInt(SHARED_ID, ID_DEFAULT)
        context?.let { databaseManager = DatabaseManager(it) }
        listTodoSQL = idDefault?.let { databaseManager.getAllTodo(it) }!!
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