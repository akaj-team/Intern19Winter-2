package asiantech.internship.summer.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_login.*
import kotlinx.android.synthetic.`at-hauha`.fragment_registry.*
import kotlinx.android.synthetic.`at-hauha`.fragment_registry.txtCreateAccount
import kotlinx.android.synthetic.`at-hauha`.fragment_login.txtLogin as txtLogin1

class FragmentLogin : Fragment(){
    companion object{
        fun getInstance() = FragmentLogin()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtCreateAccount.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer,FragmentProfile.getInstance(),null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        txtLogin.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer,FragmentProfile.getInstance(),null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }
}