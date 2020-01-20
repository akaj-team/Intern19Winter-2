package asiantech.internship.summer.layout


import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_user_profile.*


/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment() {
    private var mName = ""
    private var mEmail = ""
    private var mAvatar: String = ""

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        private const val ARG_AVATAR = "avatar"

        fun newInstance(mName: String, mEmail: String) = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, mName)
                putString(ARG_EMAIL, mEmail)
            }
        }

        fun getAvatar(mAvatar: String) = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_AVATAR, mAvatar)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mName = it.getString(ARG_NAME).toString()
            mEmail = it.getString(ARG_EMAIL).toString()
            mAvatar = it.getString(ARG_AVATAR).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvName.text = mName
        val bitmap = BitmapFactory.decodeFile(mAvatar)
        imgAvatar.setImageBitmap(bitmap)
        imgEditProfile.setOnClickListener {
            (activity as LayoutMainActivity).replaceFragment(EditProfileFragment.newInstance(mName, mEmail))
        }
    }

}
