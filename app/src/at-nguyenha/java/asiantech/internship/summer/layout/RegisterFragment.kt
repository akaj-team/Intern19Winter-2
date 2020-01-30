package asiantech.internship.summer.layout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
