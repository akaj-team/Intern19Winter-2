package asiantech.internship.summer.savedata

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
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
import asiantech.internship.summer.savedata.Utils.PUT_ID
import asiantech.internship.summer.savedata.Utils.SHARE_REF
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
                user?.id?.let { it1 -> saveLogin(it1) }
                user?.id?.let { it2 -> (activity as? TodoActivity)?.replaceMenuFragment(it2)}

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
            (activity as? TodoActivity)?.replaceRegisterFragment("")
        }
    }

    private fun saveLogin(id : Int){
        val sharedPreferences = requireContext()
                .getSharedPreferences(SHARE_REF, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(PUT_ID, id)
        editor.apply()
    }
}