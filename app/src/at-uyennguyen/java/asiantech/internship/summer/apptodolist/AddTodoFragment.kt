package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.main.fragment_add_todo.*

class AddTodoFragment : Fragment() {

    companion object {
        private const val SHARED_ID = "id"
        private const val USER = "user"
        private const val ID_DEFAULT = -1
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
        fun newInstance(user: User): AddTodoFragment {
            val addTodoFragment = AddTodoFragment()
            val bundle = Bundle()
            bundle.putParcelable(USER, user)
            addTodoFragment.arguments = bundle
            return addTodoFragment
        }
    }
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var databaseManager: DatabaseManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_todo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        val idDefault = sharedPreferences?.getInt(SHARED_ID,ID_DEFAULT)
        btnAddTodo.setOnClickListener {
            context?.let { databaseManager = DatabaseManager(it) }
            val user = arguments?.getParcelable<User>(USER) as User
            val textTodo : String = edtTodo.text.toString()
            val todo = idDefault?.let { it1 -> Todo(idTodo = 0, idUserTodo = it1, textTodo = textTodo) }
            if (todo != null) {
                databaseManager.addTodo(todo)
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frameLayoutTodo, HomeToDoFragment(user))
                        ?.commit()

            }

        }
    }
}