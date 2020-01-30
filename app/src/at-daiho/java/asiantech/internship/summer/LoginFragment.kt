package asiantech.internship.summer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.`at-daiho`.fragment_login.*


class LoginFragment : Fragment() {

    companion object {
        fun getInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSignUp.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flRootContainer, SignUpFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        btLogin.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flRootContainer, EditProfileFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

}
