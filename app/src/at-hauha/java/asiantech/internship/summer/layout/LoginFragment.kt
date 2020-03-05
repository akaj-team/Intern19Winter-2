package asiantech.internship.summer.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_registry.*
import kotlinx.android.synthetic.`at-hauha`.fragment_registry.tvCreateAccount

class LoginFragment : Fragment() {
    companion object {
        fun getInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCreateAccount.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, ProfileFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        tvLogin.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, ProfileFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }
}