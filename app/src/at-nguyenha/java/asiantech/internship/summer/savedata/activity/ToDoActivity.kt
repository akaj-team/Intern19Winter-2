package asiantech.internship.summer.savedata.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.ToDoAdapter
import kotlinx.android.synthetic.`at-nguyenha`.activity_to_do.*

class ToDoActivity : AppCompatActivity() {

    companion object {
        const val ACTION_EDIT = "edit"
    }

    private val adapterToDO = ToDoAdapter(supportFragmentManager)

    private val action = "edit"

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
            intent.putExtra(ACTION_EDIT, action)
            startActivity(intent)
        }
        layoutAddToDoItem.setOnClickListener {
            val intent = Intent(this, EditToDoActivity::class.java)
            startActivity(intent)
        }
        layoutLogoutItem.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
