package asiantech.internship.summer.layoutandroid

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_user_profile.*

class UserProfileFragment : Fragment() {
    private var avatar: String = ""

    companion object {
        const val NAME: String = "name"
        const val EMAIL: String = "email"
        const val AVATAR: String = "avatar"
        const val BIO: String = "bio"
        fun newProfile(name: String, email: String, avatar: String, bio: String): UserProfileFragment {
            val userProfileFragment = UserProfileFragment()
            val bundle = Bundle()
            bundle.putString(NAME, name)
            bundle.putString(EMAIL, email)
            bundle.putString(AVATAR, avatar)
            bundle.putString(BIO, bio)
            userProfileFragment.arguments = bundle
            return userProfileFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailEdit = arguments?.getString(EMAIL).toString()
        tvNameUser.text = arguments?.getString(NAME)
        tvBio.text = arguments?.getString(BIO)
        if (arguments?.getString(AVATAR) != "") {
            avatar = arguments?.getString(AVATAR).toString()
            imgAvatar.setImageURI(Uri.parse(avatar))
        } else {
            imgAvatar.setImageResource(R.drawable.img_avatarman)
        }
        icPencil.setOnClickListener {
            val editProfileFragment: EditProfileFragment = EditProfileFragment.profileEdit(tvNameUser.text.toString(), emailEdit, tvBio.text.toString(), avatar)
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, editProfileFragment)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }
}
