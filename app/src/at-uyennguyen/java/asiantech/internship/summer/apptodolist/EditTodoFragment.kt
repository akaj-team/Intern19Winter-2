package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_edit_todo.*

class EditTodoFragment : Fragment() {
    companion object {
        private const val ID_DEFAULT = -1
        private const val SHARED_ID = "id"
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
        private const val TODO = "todo"
        fun newInstance(todo: Todo): EditTodoFragment {
            val editTodoFragment = EditTodoFragment()
            val bundle = Bundle()
            bundle.putParcelable(TODO, todo)
            editTodoFragment.arguments = bundle
            return editTodoFragment
        }
    }

    private lateinit var userTodo: User
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var databaseManager: DatabaseManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { databaseManager = DatabaseManager(it) }
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        val idDefault = sharedPreferences?.getInt(SHARED_ID, ID_DEFAULT)
        if (idDefault != ID_DEFAULT) {
            val user = idDefault?.let { databaseManager.getUserById(it) }
            if (user != null) {
                if (user.size > 0 && user[0].idUser != -1) {
                    userTodo = user[0]
                }
            }
            val todo = arguments?.getParcelable<Todo>(TODO) as Todo
            edtEditTodo.setText(todo.textTodo)
            btnEditTodo.setOnClickListener {
                val idTodo = todo.idTodo
                val idUserTodo = todo.idUserTodo
                val textTodo: String = edtEditTodo.text.toString()
                val todoEdit = Todo(idTodo = idTodo, idUserTodo = idUserTodo, textTodo = textTodo)
                databaseManager.updateTodo(todoEdit)
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frameLayoutTodo, HomeToDoFragment(userTodo))
                        ?.addToBackStack(null)
                        ?.commit()
            }
        }
    }
}
