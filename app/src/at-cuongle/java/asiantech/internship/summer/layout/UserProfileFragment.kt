package asiantech.internship.summer.layout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.`at-cuongle`.fragment_user_profile.*

class UserProfileFragment : Fragment() {
    private var mName = ""
    private var mEmail = ""
    private var mAvatar = ""
    private lateinit var tabLayout: TabLayout

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
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        tabLayout = view.findViewById(R.id.tabLayout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvName.text = mName
        if (mAvatar != "") {
            imgAvatar.setImageURI(mAvatar.toUri())
        }
//        imgEditProfile.setOnClickListener {
//            (activity as LayoutMainActivity).replaceFragment(EditProfileFragment.newInstance(mName, mEmail, mAvatar))
//        }
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = FragmentPagerAdapter(childFragmentManager)
        adapter.apply {
            addFragment(RecipesFragment(), getString(R.string.tablayout_title_recipes))
            addFragment(RecipesFragment(), getString(R.string.tablayout_title_saved))
            addFragment(RecipesFragment(), getString(R.string.tablayout_title_following))
        }
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
