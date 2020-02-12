package asiantech.internship.summer.drawerlayout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-daiho`.activity_drawer_layout.*

class DrawerLayoutActivity : AppCompatActivity() {

    private val menus = mutableListOf<Menu>()
    private lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)
        initData()
        initAdapter()
        drawerSlide()
    }

    private fun initData() {
        menus.add(Menu("Inbox", R.drawable.ic_inbox_selected, R.drawable.ic_inbox_normal))
        menus.add(Menu("Sent", R.drawable.ic_send_normal, R.drawable.ic_send_normal))
        menus.add(Menu("Star", R.drawable.ic_star_selected, R.drawable.ic_star_normal))
        menus.add(Menu("Spam", R.drawable.ic_block_selected, R.drawable.ic_block_normal))
    }

    private fun initAdapter() {
        adapter = MenuAdapter(menus)
        recyclerViewLeftMenu.adapter = adapter
    }

    private fun drawerSlide() {
        val drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slide: Float = drawerView.width * slideOffset
                container.translationX = slide
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
    }
}
