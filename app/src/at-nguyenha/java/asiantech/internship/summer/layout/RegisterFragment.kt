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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.
                    replace(R.id.frLayout,LoginFragment(),null)?.
                    addToBackStack(null)?.commit()}
    }

}
