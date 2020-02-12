package asiantech.internship.summer.layoutandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_user_profile.*

class UserProfileFragment : Fragment() {
    companion object {
        const val NAME: String = "name"
        const val EMAIL: String = "email"
        fun newProfile(name: String, email: String): UserProfileFragment {
            val userProfileFragment = UserProfileFragment()
            val bundle = Bundle()
            bundle.putString(NAME, name)
            bundle.putString(EMAIL, email)
            userProfileFragment.arguments = bundle
            return userProfileFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailEdit: String
        emailEdit = arguments?.getString(EMAIL).toString()
        tvName.setText(arguments?.getString(NAME))
        imgPencil.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val editProfileFragment: EditProfileFragment = EditProfileFragment.profileEdit(tvName.text.toString(), emailEdit, tvAddress.text.toString(), imgAvatar)
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayout, editProfileFragment)
                        ?.addToBackStack(null)
                        ?.commit()
            }
        })
    }
}
