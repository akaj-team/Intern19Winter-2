package asiantech.internship.summer.savedata.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.Utils
import asiantech.internship.summer.savedata.adapter.ToDoAdapter
import kotlinx.android.synthetic.`at-nguyenha`.activity_to_do.*

class ToDoActivity : AppCompatActivity() {

    private val adapterToDO = ToDoAdapter(supportFragmentManager)
    private lateinit var setAction : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        initView()
        initListener()
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

    private fun initListener() {
        layoutEditProfileItem.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            setAction = Utils.ACTION_EDIT_PROFILE
            intent.putExtra(Utils.ACTION, setAction)
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
