package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_home_todo.*

class HomeToDoFragment(private val user: User) : Fragment() {
//
//    companion object {
//        private const val USER = "user"
//        fun newInstance(user: User): LoginToDoFragment {
//            val loginToDoFragment = LoginToDoFragment()
//            val bundle= Bundle()
//            bundle.putParcelable(USER, user)
//            loginToDoFragment.arguments = bundle
//            return loginToDoFragment
//        }
//    }

    var items = arrayListOf<DrawerItem>()
    private lateinit var adapter:HomeToDoAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initData()
    }

    fun initAdapter() {
        adapter = context?.let { HomeToDoAdapter(items,user, it) }!!
        recyclerViewProfile.adapter = adapter
        adapter.onItemClicked = {
            val editProfileFragment = EditProfileFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayoutTodo,editProfileFragment)
                    ?.commit()
        }
    }

    fun initData() {
        items.add(DrawerItem(R.drawable.ic_edit_profile, "Edit profile", R.drawable.ic_edit_profile_color))
        items.add(DrawerItem(R.drawable.ic_edit_profile, "Edit profile", R.drawable.ic_edit_profile_color))
        items.add(DrawerItem(R.drawable.ic_add_todo, "Add todo", R.drawable.ic_add_todo_color))
        items.add(DrawerItem(R.drawable.ic_logout, "Logout", R.drawable.ic_logout_color))
        adapter.notifyDataSetChanged()
    }
}
