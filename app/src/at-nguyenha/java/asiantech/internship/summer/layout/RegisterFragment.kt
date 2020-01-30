package asiantech.internship.summer.layout


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import asiantech.internship.summer.MainActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.fragment_register.*


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {
    private var mName = ""
    private var mEmail = ""

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        fun newInstance(mName: String, mEmail: String) = RegisterFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, mName)
                putString(ARG_EMAIL, mEmail)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mName = it.getString(ARG_NAME).toString()
            mEmail = it.getString(ARG_EMAIL).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtFullname.setText(mName)
        edtEmailRegister.setText(mEmail)
        btnRegister.setOnClickListener {
            mName = edtFullname.text.toString()
            mEmail = edtEmailRegister.text.toString()
            (activity as? MyMainActiviry)?.replaceFragment(LoginFragment.newInstance(mName, mEmail))
        }
    }

}
