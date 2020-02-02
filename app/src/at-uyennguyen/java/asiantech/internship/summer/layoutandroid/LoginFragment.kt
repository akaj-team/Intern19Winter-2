package asiantech.internship.summer.layoutandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_login.*

class LoginFragment : Fragment(){
    companion object{
        val EMAIL: String="email"
        val PASS:String="pass"
        val NAME:String="name"
        fun newInstance(email:String,pass:String,name:String): LoginFragment {
            val loginFragment=LoginFragment()
            val bundle=Bundle()
            bundle.putString(EMAIL,email)
            bundle.putString(PASS,pass)
            bundle.putString(NAME,name)
            loginFragment.arguments=bundle
            return loginFragment
        }
    }
//    override fun profileLogin(fullName: String, email: String, password: String) {
//        editEmail.setText(email)
//        editPass.setText(password)
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editEmail.setText(arguments?.getString(EMAIL))
        editPass.setText(arguments?.getString(PASS))
        var fullName : String? =arguments?.getString(NAME)
        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                    activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frameLayout,UserProfileFragment.newProfile(fullName.toString(),editEmail.text.toString()))
                            ?.addToBackStack(null)
                            ?.commit()
                }
        })
    }
}
