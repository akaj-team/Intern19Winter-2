package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_login_todo.*

class LoginToDoFragment : Fragment(){
    companion object{
        var ID_DEFAULT = -1
        private val SHARED_ID = "id"
    }
    private var sharedPreferences: SharedPreferences ?= null
    private var SHARED_PREFERENCES_NAME = "sharepreferences"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_todo,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply { sharedPreferences= this?.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)}
        var idDefault = sharedPreferences?.getInt(SHARED_ID,ID_DEFAULT)
        if(idDefault!= ID_DEFAULT){
            var homeToDoFragment = HomeToDoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, homeToDoFragment)
                    ?.commit()
        }
        else{
            btnLogin.setOnClickListener {

            }
        }
    }
}