package asiantech.internship.summer.layoutandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_register.*

class RegisterFragment : Fragment() {
    var profile:Profile?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                profile?.profileLogin(editName.text.toString(), editEmail1.text.toString(),editPass1.text.toString())
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frameLayout, LoginFragment.newInstance(editEmail1.text.toString(),editPass1.text.toString(),editName.text.toString()))
                        ?.addToBackStack(null)
                        ?.commit()
            }
        })
    }
}
