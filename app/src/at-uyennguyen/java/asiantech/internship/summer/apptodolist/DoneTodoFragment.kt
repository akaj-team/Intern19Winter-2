package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_done_todo.*

@Suppress("DEPRECATION")
class DoneTodoFragment : Fragment() {

    companion object {
        private const val ID_DEFAULT = -1
        private const val SHARED_ID = "id"
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
    }

    private var idDefault: Int = -1
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var databaseManager: DatabaseManager
    private var listTodoSQL = arrayListOf<Todo>()
    private var listTodo = arrayListOf<Todo>()
    private lateinit var adapterDoneTodo: DoneTodoAdapter
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            initAdapter()
            initData()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        idDefault = sharedPreferences?.getInt(SHARED_ID, ID_DEFAULT)!!
        context?.let { databaseManager = DatabaseManager(it) }
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        listTodoSQL = databaseManager.getAllDoneTodo(idDefault)
        adapterDoneTodo = DoneTodoAdapter(listTodo)
        recyclerViewDoneTodo.adapter = adapterDoneTodo
    }

    private fun initData() {
        for (i in 0..listTodoSQL.size - 1) {
            listTodo.add(listTodoSQL[i])
        }
        adapterDoneTodo.notifyDataSetChanged()
    }
}
