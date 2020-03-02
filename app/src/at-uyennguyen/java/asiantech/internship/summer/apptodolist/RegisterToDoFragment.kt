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

class RegisterToDoFragment : Fragment(){
    companion object{
        var ID_DEFAULT = -1
        private val SHARED_ID = "id"
    }
    private var sharedPreferences: SharedPreferences ?= null
    private var SHARED_PREFERENCES_NAME = "sharepreferences"
    private var databaseManager: DatabaseManager ?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_register_todo,container,false)
        context?.apply{databaseManager= DatabaseManager(this)}
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply { sharedPreferences= this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)}
        var idDefault = sharedPreferences?.getInt(SHARED_ID,ID_DEFAULT)
        Log.d("frag", idDefault.toString())
        if(idDefault!= ID_DEFAULT){
            val homeToDoFragment = HomeToDoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutTodo, homeToDoFragment)
                    ?.commit()
        }
        else{
            btnRegister.setOnClickListener {
                var name : String = edtUsernameRegister.text.toString()
                var nickname : String = edtNicknameRegister.text.toString()
                var password : String = edtPasswordRegister.text.toString()
                var user = User(idUser = 0,nameUser = name,nickName = nickname,passWord = password)
                databaseManager?.addUser(user)
                activity?.supportFragmentManager?.beginTransaction()
            }
        }
    }

}