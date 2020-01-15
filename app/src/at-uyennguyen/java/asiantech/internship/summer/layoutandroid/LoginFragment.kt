package asiantech.internship.summer.layoutandroid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_login.*

class LoginFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                val signinFragment: SigninFragment = SigninFragment()
                fragmentTransaction?.replace(R.id.frameLayout, signinFragment)?.commit()
            }
        })
    }
}