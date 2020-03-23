package asiantech.internship.summer.savedata.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.DrawerAdapter
import asiantech.internship.summer.savedata.adapter.ToDoAdapter
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.AccountModel
import asiantech.internship.summer.savedata.model.MenuModel
import asiantech.internship.summer.savedata.model.Utils
import kotlinx.android.synthetic.`at-nguyenha`.activity_to_do.*

class ToDoActivity : AppCompatActivity() {

    private var account: AccountModel? = null
    private var db: ConnectDataBase? = null
    private val adapterToDO = ToDoAdapter(supportFragmentManager)
    private lateinit var setAction : String
    private var idLogined = -1
    private var adapterDrawer: DrawerAdapter? = null
    private var listMenu: MutableList<MenuModel>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            idLogined = bundle.getInt(Utils.PUT_ID_ACCOUNT)
        }

        db = ConnectDataBase.getInMemoryDatabase(this)
        initView()
        initAdapterDrawer()
    }

    override fun onRestart() {
        super.onRestart()
        initDataDrawer()
        adapterDrawer?.notifyDataSetChanged()
    }

    private fun initView() {
        vpToDo.adapter = adapterToDO
        tabToDo.setupWithViewPager(vpToDo)
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerToDo,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX: Float = drawerView.width * slideOffset
                layoutToDo.translationX = slideX
            }
        }
        drawerToDo.addDrawerListener(actionBarDrawerToggle)
    }

    private fun initAdapterDrawer() {
        initDataDrawer()
        adapterDrawer = listMenu?.let { DrawerAdapter(it) }
        recyclerDrawer.adapter = adapterDrawer
        recyclerDrawer.layoutManager = LinearLayoutManager(this)

        adapterDrawer?.onClickEditProfile = {
            val intent = Intent(this, EditProfileActivity::class.java)
            setAction = Utils.ACTION_EDIT_PROFILE
            val bundle = Bundle()
            bundle.putString(Utils.ACTION, setAction)
            bundle.putInt(Utils.PUT_ID_ACCOUNT, idLogined)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        adapterDrawer?.onClickAddTodo = {
            val intent = Intent(this, AddEditToDoActivity::class.java)
            setAction = Utils.ACTION_ADD
            intent.putExtra(Utils.ACTION, setAction)
            startActivity(intent)
        }

        adapterDrawer?.onClickLogout = {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            removeSharedPreferences()
            finish()
        }
    }

    private fun initDataDrawer() {
        account = db?.accountDao()?.getAccountById(idLogined)
        listMenu?.clear()
        listMenu?.add(MenuModel(account?.avatarAccount!!, account?.nickName!!))
        listMenu?.add(MenuModel("", getString(R.string.textview_text_edit_profile)))
        listMenu?.add(MenuModel("", getString(R.string.textview_text_add_todo)))
        listMenu?.add(MenuModel("", getString(R.string.textview_text_logout)))
    }

    private fun removeSharedPreferences(){
        val sharedPreferences = this
                .getSharedPreferences(Utils.SHARED_PREFS, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(Utils.PUT_ID_ACCOUNT, Utils.ID_DEFAULT)
        editor.apply()
    }
}
