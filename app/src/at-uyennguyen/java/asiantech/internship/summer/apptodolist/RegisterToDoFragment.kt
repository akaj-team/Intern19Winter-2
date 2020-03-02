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
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_register_todo.*

class RegisterToDoFragment : Fragment() {
    companion object {
        private const val ID_DEFAULT = -1
        private const val SHARED_ID = "id"
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
    }

    private var sharedPreferences: SharedPreferences? = null
    private var databaseManager: DatabaseManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_todo, container, false)
        context?.apply { databaseManager = DatabaseManager(this) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        val idDefault = sharedPreferences?.getInt(SHARED_ID, ID_DEFAULT)
        Log.d("frag", idDefault.toString())
        if (idDefault != ID_DEFAULT) {
            val homeToDoFragment = HomeToDoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutTodo, homeToDoFragment)
                    ?.commit()
        } else {
            btnRegister.setOnClickListener {
                val name: String = edtUsernameRegister.text.toString()
                val nickname: String = edtNicknameRegister.text.toString()
                val password: String = edtPasswordRegister.text.toString()
                val user = User(idUser = 0, nameUser = name, nickName = nickname, passWord = password)
                databaseManager?.addUser(user)
                val homeToDoFragment = HomeToDoFragment()
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frameLayoutTodo, homeToDoFragment)
                        ?.commit()
            }
        }
        tvLoginhere.setOnClickListener {
            val loginToDoFragment = LoginToDoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutTodo, loginToDoFragment)
                    ?.commit()
        }
    }
}
