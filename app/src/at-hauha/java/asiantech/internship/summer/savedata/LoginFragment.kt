package asiantech.internship.summer.savedata

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.User
import kotlinx.android.synthetic.`at-hauha`.fragment_data_login.*

@Suppress("NAME_SHADOWING")
class LoginFragment : Fragment() {

    private var user: User? = null
    private var db: AppDatabase? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_data_login, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "<font color=#000000>New user?</font> <font color=#329AE7>Sign-up</font>"
        tvSignUp.text = Html.fromHtml(text,1)

        db = AppDatabase.connectDatabase(requireContext())
        tvLogin.setOnClickListener {
            user = db?.userDao()?.findUser(edtEmail.text.toString(), edtPassword.text.toString())
            if (user != null) {
                user?.let { it -> (activity as? TodoActivity)?.replaceMenuFragment(it)}
                edtEmail.text = Editable.Factory.getInstance().newEditable("")
                edtPassword.text = Editable.Factory.getInstance().newEditable("")
            } else {
                val dialog = AlertDialog.Builder(requireContext())
                with(dialog) {
                    setTitle(getString(R.string.alert))
                    show()
                }
            }
        }
        tvSignUp.setOnClickListener {
            (activity as? TodoActivity)?.replaceRegisterFragment()
        }
    }
}