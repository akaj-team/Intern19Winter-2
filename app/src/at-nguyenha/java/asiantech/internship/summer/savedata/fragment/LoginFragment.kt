package asiantech.internship.summer.savedata.fragment


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.activity.ToDoActivity
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.AccountModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_login_save_data.*

class LoginFragment : Fragment() {

    private var account: AccountModel? = null
    private var db: ConnectDataBase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_save_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        btnLoginSaveData.setOnClickListener {
            db = ConnectDataBase.getInMemoryDatabase(requireContext())
            val username = edtUserName.text.toString()
            val password = edtPasswordLogin.text.toString()
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please enter username", Toast.LENGTH_SHORT).show()
            } else {
                account = db?.accountDao()?.checkLogin(username, password)
                if (account != null) {
                    val intent = Intent(activity, ToDoActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Username/password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
