package asiantech.internship.summer.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import android.net.Uri
import android.util.Log
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.`at-hauha`.fragment_user_profile.*

class UserProfileFragment : Fragment() {

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        private const val ARG_AVATAR = "avatar"

        fun newInstance(mName: String, mEmail: String, mAvatar: String) = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, mName)
                putString(ARG_EMAIL, mEmail)
                putString(ARG_AVATAR, mAvatar)
            }
        }
    }

    private var mName = ""
    private var mEmail = ""
    private var mAvatar = ""
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        tabLayout = view.findViewById(R.id.tabs)
        return view
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
            tvUserName.text = mName
        }
        tvEdit.setOnClickListener {
            mName = tvUserName.text.toString()
            Log.d("XXX", mAvatar)
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, EditProfileFragment.newInstance(mName, mEmail, mAvatar), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        setViewPage()
    }

    private fun setViewPage() {
        val adapter = FoodAdapter(childFragmentManager)
        adapter.apply {
            addFragment(RecipesFragment(), "Recipes")
            addFragment(RecipesFragment(), "Saved")
            addFragment(RecipesFragment(), "Following")
        }
        viewPager!!.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
