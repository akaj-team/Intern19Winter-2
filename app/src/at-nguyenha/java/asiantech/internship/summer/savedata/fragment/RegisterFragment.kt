package asiantech.internship.summer.savedata.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R

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
        return inflater.inflate(R.layout.fragment_register_savedata, container, false)
    }
}
