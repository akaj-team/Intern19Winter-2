package asiantech.internship.summer.savedata
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.TodoActivity
import kotlinx.android.synthetic.`at-hauha`.fragment_data_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_data_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "<font color=#000000>New user?</font> <font color=#329AE7>Sign-up</font>"
        tvSignUp.text = Html.fromHtml(text)
        tvLogin.setOnClickListener {
            (activity as TodoActivity)?.replaceMenuFragment()
        }
    }
}