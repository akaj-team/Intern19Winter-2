package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_home_todo.*

class HomeToDoFragment(private val user: User) : Fragment() {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
    }

    private var listFragment = arrayListOf<ListFragment>()
    private lateinit var adapterTabLayout: TabLayoutAdapter
    private var sharedPreferences: SharedPreferences? = null
    private var items = arrayListOf<DrawerItem>()
    private lateinit var adapter: HomeToDoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        adapter = context?.let { HomeToDoAdapter(items, user, it) }!!
        recyclerViewProfile.adapter = adapter
        adapter.onItemClicked = {
            if (it == 1) {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayoutTodo, EditProfileFragment.newInstance(user))
                        ?.addToBackStack(null)
                        ?.commit()
            }
            if (it == 2) {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayoutTodo, AddTodoFragment.newInstance(user))
                        ?.addToBackStack(null)
                        ?.commit()
            }
            if (it == 3) {
                removeSharedPreference()
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayoutTodo, LoginToDoFragment())
                        ?.commit()
            }
        }
    }

    private fun initData() {
        items.add(DrawerItem(R.drawable.ic_edit_profile, "Edit profile", R.drawable.ic_edit_profile_color))
        items.add(DrawerItem(R.drawable.ic_edit_profile, "Edit profile", R.drawable.ic_edit_profile_color))
        items.add(DrawerItem(R.drawable.ic_add_todo, "Add todo", R.drawable.ic_add_todo_color))
        items.add(DrawerItem(R.drawable.ic_logout, "Logout", R.drawable.ic_logout_color))
        adapter.notifyDataSetChanged()
    }

    private fun setViewPager() {
        listFragment.add(ListFragment(TodoFragment(), "TODO"))
        listFragment.add(ListFragment(DoneTodoFragment(), "DONE"))
        adapterTabLayout = TabLayoutAdapter(childFragmentManager, listFragment)
        viewPagerOfTabLayout?.adapter = adapterTabLayout
        tabLayoutTodo?.setupWithViewPager(viewPagerOfTabLayout)
    }

    private fun removeSharedPreference() {
        context?.apply { sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }
}
