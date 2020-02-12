package asiantech.internship.summer.layoutandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_register.*

class RegisterFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSign.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frameLayout, LoginFragment.newInstance(edtEmail1.text.toString(), edtPass1.text.toString(), edtName.text.toString()))
                        ?.addToBackStack(null)
                        ?.commit()
            }
        })
    }
}
