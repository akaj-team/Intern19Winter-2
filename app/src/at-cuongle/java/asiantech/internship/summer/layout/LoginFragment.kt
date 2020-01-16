package asiantech.internship.summer.layout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_login.*

//import asiantech.internship.summer.cuongle.debug.R

/**
 * A simple [Fragment] subclass.
 */
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
        btnLogin.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, SignUpFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

}
