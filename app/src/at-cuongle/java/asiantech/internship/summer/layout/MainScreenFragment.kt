package asiantech.internship.summer.layout

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.DataConnection
import asiantech.internship.summer.layout.database.model.User
import kotlinx.android.synthetic.`at-cuongle`.fragment_main_screen.*

class MainScreenFragment : Fragment() {
    companion object {
        private const val ARG_IS_LOGIN = "isLogin"
        private const val ARG_PREFERENCES = "MyPref"
        private const val ARG_USER_EMAIL = "userEmail"
        private const val DEFAULT_VALUE = ""
    }

    private var db: DataConnection? = null
    private val itemMenu = mutableListOf<Menu?>()
    private lateinit var menuAdapter: MenuAdapter
    private var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataConnection.connectData(requireContext())
        initAdapter()
        initView()
        initData()
    }

    private fun initAdapter() {
        menuAdapter = MenuAdapter(itemMenu)
        recyclerViewMenu.adapter = menuAdapter
        menuAdapter.onItemMenuClicked = { it1 ->
            Toast.makeText(context, itemMenu[it1]?.item, Toast.LENGTH_LONG).show()
            when (itemMenu[it1]?.item) {
                getString(R.string.title_menu_edit) -> {
                    user?.let { (activity as? TodoMainActivity)?.replaceFragment(EditProfileFragment.newInstance(it)) }
                }
                getString(R.string.title_menu_logout) -> {
                    removeLoginStatus()
                    (activity as? TodoMainActivity)?.replaceFragment(UserLoginFragment())
                }
            }
        }
    }

    private fun initView() {
        val adapter = TabLayoutAdapter(childFragmentManager)
        adapter.apply {
            addFragment(ToDoFragment(), getString(R.string.tab_layout_todo))
            addFragment(DoneFragment(), getString(R.string.tab_layout_done))
        }
        viewPagerTodo.adapter = adapter
        tabLayoutTodo.setupWithViewPager(viewPagerTodo)
        tabLayoutTodo.setSelectedTabIndicatorColor(Color.BLACK)
        tabLayoutTodo.setBackgroundColor(Color.BLUE)
    }

    private fun initData() {
        user = db?.userDao()?.findByEmail(getUserName())
        itemMenu.apply {
            add(user?.path?.let { Menu(0, it) })
            add(Menu(R.drawable.ic_border_color_black_24dp, getString(R.string.title_menu_edit)))
            add(Menu(R.drawable.ic_lock_outline_black_24dp, getString(R.string.title_menu_logout)))
        }
        menuAdapter.notifyDataSetChanged()
    }

    private fun removeLoginStatus() {
        val sharedPreferences = requireContext().getSharedPreferences(ARG_PREFERENCES, 0)
        val editor = sharedPreferences.edit()
        editor.remove(ARG_IS_LOGIN)
                .remove(ARG_USER_EMAIL)
                .apply()
    }

    private fun getUserName(): String {
        val sharedPreferences = requireContext().getSharedPreferences(ARG_PREFERENCES, 0)
        return sharedPreferences?.getString(ARG_USER_EMAIL, DEFAULT_VALUE) ?: DEFAULT_VALUE
    }
}
