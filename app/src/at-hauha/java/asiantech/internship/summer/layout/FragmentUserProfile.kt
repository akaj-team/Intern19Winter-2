package asiantech.internship.summer.layout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import android.net.Uri
import android.util.Log
import android.widget.Toast
import asiantech.internship.summer.Activity
import kotlinx.android.synthetic.`at-hauha`.fragment_user_profile.*


class FragmentUserProfile : Fragment() {
    private var mName = ""
    private var mEmail = ""
    private var mAvatar = ""

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        private const val ARG_AVATAR = "avatar"

        fun newInstance(mName: String, mEmail: String, mAvatar: String) = FragmentUserProfile().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, mName)
                putString(ARG_EMAIL, mEmail)
                putString(ARG_AVATAR, mAvatar)
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mName = it.getString(ARG_NAME).toString()
            mEmail = it.getString(ARG_EMAIL).toString()
            mAvatar = it.getString(ARG_AVATAR).toString()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mAvatar != "") {
            imgAvatar.setImageURI(Uri.parse(mAvatar))
            txtUserName.text = mName
        }
        //txtUserName.setText(mName)
        txtEdit.setOnClickListener {
            mName = txtUserName.text.toString()
            Log.d("XXX",mAvatar)
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, FragmentProfile.newInstance(mName, mEmail,mAvatar), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }
}