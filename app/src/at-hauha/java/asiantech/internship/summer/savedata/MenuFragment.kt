package asiantech.internship.summer.savedata

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.DoneFragment
import asiantech.internship.summer.savedata.adapter.DrawerAdapter
import asiantech.internship.summer.savedata.adapter.MenuAdapter
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.DrawerItem
import asiantech.internship.summer.savedata.model.Todo
import asiantech.internship.summer.savedata.model.User
import kotlinx.android.synthetic.`at-hauha`.fragment_menu.*

class MenuFragment : Fragment() {

    private val drawerList = mutableListOf<DrawerItem>()
    private lateinit var adapterDrawer: DrawerAdapter
    private var user: User? = null
    private var todo: Todo? = null
    private lateinit var dialogAdd: Dialog
    private var db: AppDatabase? = null

    companion object {
        private const val ARG_USER = "user"
        fun newInstance(user: User) = MenuFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_USER, user)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            user = getSerializable(ARG_USER) as User?
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPager()
        initData()
        initAdapter()
    }

    private fun initListener() {
        adapterDrawer = DrawerAdapter(drawerList)
        adapterDrawer.onItemClicked = {
            when (it) {
                1 -> user?.let { it1 -> (activity as? TodoActivity)?.replaceEditProfileFragment(it1) }
                2 -> showDialog()
            }
        }

    }

    private fun initAdapter() {
        initListener()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterDrawer
    }

    private fun initData() {
        val path = user?.path
        val name = user?.username
        drawerList.apply {
            path?.let { name?.let { it1 -> DrawerItem(it, it1) } }?.let { add(it) }
            add(DrawerItem(R.drawable.ic_people_black_24dp.toString(), "Edit profile"))
            add(DrawerItem(R.drawable.ic_playlist_add_black_24dp.toString(), "Add todo"))
            add(DrawerItem(R.drawable.ic_notifications_none_black_24dp.toString(), ""))
        }
    }

    private fun setPager() {
        val adapter = MenuAdapter(childFragmentManager)
        adapter.apply {
            user?.id?.let { TodoFragment.newInstance(it, false) }?.let { addFragment(it, "todo") }
            user?.id?.let { DoneFragment.newInstance(it, true) }?.let { addFragment(it, "Done") }
        }
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun showDialog() {

        dialogAdd = Dialog(requireContext())
        dialogAdd.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.dialog_add)
        }
        db = AppDatabase.connectDatabase(requireContext())
        val edtTodo: EditText = dialogAdd.findViewById(R.id.edtTodo)
        val btnCancel: Button = dialogAdd.findViewById(R.id.btnCancel)
        val btnAdd: Button = dialogAdd.findViewById(R.id.btnAdd)
        btnCancel.setOnClickListener {
            user?.let { it1 -> (activity as? TodoActivity)?.replaceMenuFragment(it1) }
            dialogAdd.cancel()
        }
        btnAdd.setOnClickListener {
            todo = user?.id?.let { it1 -> Todo(todo = edtTodo.text.toString(), isStatus = false, userId = it1) }
            todo?.let { it1 -> db?.todoDao()?.insertTodo(it1) }
            dialogAdd.cancel()
        }
        dialogAdd.show()
        dialogAdd.window?.setLayout(1400, 800)
    }
}

