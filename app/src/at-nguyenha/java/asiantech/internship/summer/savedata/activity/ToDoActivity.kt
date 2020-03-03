package asiantech.internship.summer.savedata.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.Utils
import asiantech.internship.summer.savedata.adapter.ToDoAdapter
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.AccountModel
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import kotlinx.android.synthetic.`at-nguyenha`.activity_to_do.*

class ToDoActivity : AppCompatActivity() {

    private var account: AccountModel? = null
    private var db: ConnectDataBase? = null
    private val adapterToDO = ToDoAdapter(supportFragmentManager)
    private lateinit var setAction : String
    private var id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            id = bundle.getInt(Utils.PUT_ID)
        }
        db = ConnectDataBase.getInMemoryDatabase(this)
        initView()
        initListener()
        initDrawer()
    }

    override fun onResume() {
        super.onResume()
        initDrawer()
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

    private fun initDrawer() {
        account = db?.accountDao()?.getAccountById(id)
        if (account != null) {
            tvNickName.text = account?.nickName
            Glide.with(drawerToDo).load(Uri.parse(account?.avatarAccount))
                    .placeholder(R.drawable.ic_account)
                    .into(imgAvatar)
        }
    }

    private fun initListener() {
        layoutEditProfileItem.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            setAction = Utils.ACTION_EDIT_PROFILE
            val bundle = Bundle()
            bundle.putString(Utils.ACTION, setAction)
            bundle.putInt(Utils.PUT_ID, id)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        layoutAddToDoItem.setOnClickListener {
            val intent = Intent(this, AddEditToDoActivity::class.java)
            setAction = Utils.ACTION_ADD
            intent.putExtra(Utils.ACTION, setAction)
            startActivity(intent)
        }
        layoutLogoutItem.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
