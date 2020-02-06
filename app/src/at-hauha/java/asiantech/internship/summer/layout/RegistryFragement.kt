package asiantech.internship.summer.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_registry.*

class RegistryFragement : Fragment() {
    private var mName = ""
    private var mEmail = ""

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        fun newInstance(mName: String, mEmail: String) = RegistryFragement().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, mName)
                putString(ARG_EMAIL, mEmail)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            mName = it.get(ARG_NAME).toString()
            mEmail = it.get(ARG_EMAIL).toString()
        }
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvLogin.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, UserProfileFragment.newInstance(mName,mEmail,  ""), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }

        edtFullName.setText(mName)
        edtEmail.setText(mEmail)
        tvCreateAccount.setOnClickListener {
            mName = edtFullName.text.toString()
            mEmail = edtEmail.text.toString()
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, LoginFragment.newInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }
}