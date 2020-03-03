package asiantech.internship.summer.layout

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
        private const val ARG_USER = "image"
        private const val ARG_IS_LOGIN = "isLogin"
        fun newInstance(user: User) = MainScreenFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_USER, user)
            }
        }
    }

    private var db: DataConnection? = null
    private val itemMenu = mutableListOf<Menu?>()
    private lateinit var menuAdapter: MenuAdapter
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable(ARG_USER) as User
        }
    }

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
                "Edit Profile" -> {
                    user?.let { (activity as? LayoutMainActivity)?.replaceFragment(EditProfileFragment.newInstance(it)) }
                }
                "Log out" -> {
                    removeLoginStatus()
                    (activity as? LayoutMainActivity)?.replaceFragment(UserLoginFragment())
                }
            }
        }
    }

    private fun initView() {
        val adapter = TabLayoutAdapter(childFragmentManager)
        adapter.apply {
            addFragment(ToDoFragment(), "TODO")
            addFragment(DoneFragment(), "DONE")
        }
        viewPagerTodo.adapter = adapter
        tabLayoutTodo.setupWithViewPager(viewPagerTodo)
        tabLayoutTodo.setSelectedTabIndicatorColor(Color.BLACK)
        tabLayoutTodo.setBackgroundColor(Color.RED)
    }

    private fun initData() {
        user = db?.userDao()?.findByEmail(getUserName())
        Log.i("XXX",getUserName())
        itemMenu.apply {
            add(user?.path?.let { Menu(0, it) })
            add(Menu(R.drawable.ic_border_color_black_24dp, "Edit Profile"))
            add(Menu(R.drawable.ic_lock_outline_black_24dp, "Log out"))
        }
        menuAdapter.notifyDataSetChanged()
    }

    private fun removeLoginStatus() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPref", 0)
        val editor = sharedPreferences.edit()
        editor.remove(ARG_IS_LOGIN)
                .apply()
    }

    private fun getUserName(): String {
        val sharedPreferences = requireContext().getSharedPreferences("MyPref", 0)
        return sharedPreferences?.getString("userEmail", "") ?: ""
    }
}
