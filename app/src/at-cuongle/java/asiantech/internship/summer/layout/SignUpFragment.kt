package asiantech.internship.summer.layout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_sign_up.*


/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
    companion object {
        fun getInstance() = SignUpFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateAccount.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, EditProfileFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

}
