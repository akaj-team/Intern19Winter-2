package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_login_todo.*

class LoginToDoFragment : Fragment() {
    companion object {
        private const val ID_DEFAULT = -1
        private const val SHARED_ID = "id"
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
    }

    private lateinit var databaseManager: DatabaseManager
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { databaseManager = DatabaseManager(it) }
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        val idDefault = sharedPreferences?.getInt(SHARED_ID, ID_DEFAULT)
        if (idDefault != ID_DEFAULT) {
            val user = idDefault?.let { databaseManager.getUserById(it) } //val user = databaseManager.getUserById(idDefault)
            if (user != null) {
                if (user.size > 0 && user[0].idUser != -1) {
                    val homeToDoFragment = HomeToDoFragment(user[0])
                    activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frameLayoutTodo, homeToDoFragment)
                            ?.commit()
                }
            }
        } else {
            btnLogin.setOnClickListener {
                val nameLogin = edtUsernameLogin.text.toString()
                val passLogin = edtPasswordLogin.text.toString()
                val user = databaseManager.loginUser(nameLogin, passLogin)
                if (user.size > 0 && user[0].idUser != -1) {
                    databaseManager.addIdSharedPreferences(user[0].idUser)
                    val homeToDoFragment = HomeToDoFragment(user[0])
                    activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frameLayoutTodo, homeToDoFragment)
                            ?.commit()
                } else {
                    Toast.makeText(context, "Username or password is incorrect", Toast.LENGTH_LONG).show()
                }
            }
        }
        tvRegister.setOnClickListener {
            val registerToDoFragment = RegisterToDoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutTodo, registerToDoFragment)
                    ?.commit()
        }
    }
}
