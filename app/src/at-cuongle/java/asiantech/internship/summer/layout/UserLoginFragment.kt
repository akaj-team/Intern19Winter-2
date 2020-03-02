package asiantech.internship.summer.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.DataConnection
import asiantech.internship.summer.layout.database.model.User
import kotlinx.android.synthetic.`at-cuongle`.fragment_login.*
import kotlinx.android.synthetic.`at-cuongle`.fragment_login.edtEmail

class UserLoginFragment : Fragment() {
    private var mName = ""
    private var mEmail = ""
    private var db: DataConnection? = null
    private var user: User? = null
    var onOk: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        fun newInstance(mName: String, mEmail: String) = UserLoginFragment().apply {
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataConnection.connectData(requireContext())
        edtEmail.setText(mEmail)
        btnLogin.setOnClickListener {
            user = db?.userDao()?.findByName(edtEmail.text.toString(), edtPasswordSign.text.toString())
            if (user != null) {
                (activity as? LayoutMainActivity)?.replaceFragment(MainScreenFragment())
            } else {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
        dialogOption.apply {
            setTitle(getString(R.string.tv_title_create_account))
            setPositiveButton(android.R.string.ok) { _, _ ->
                onOk?.invoke()
                Toast.makeText(context, getString(R.string.toast_create_now), Toast.LENGTH_SHORT).show()
                (activity as? LayoutMainActivity)?.replaceFragment(SignUpFragment())
            }
            setNegativeButton(android.R.string.cancel) { _, _ ->
                onCancel?.invoke()
                Toast.makeText(context, getString(R.string.toast_cancel), Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }
}
