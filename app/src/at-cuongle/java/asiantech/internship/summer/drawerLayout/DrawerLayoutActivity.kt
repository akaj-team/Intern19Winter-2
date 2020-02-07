package asiantech.internship.summer.drawerLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_drawer_layout.*
import kotlinx.android.synthetic.`at-cuongle`.row_top.*

class DrawerLayoutActivity : AppCompatActivity() {
    private val items = mutableListOf<Menu>()
    private lateinit var adapterTimeLine: DrawerLayoutAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)
        initAdapter()
        initData()
        initListeners()

    }

    private fun initData() {
        items.apply {
            add(Menu(""))
            add(Menu("Inbox"))
            add(Menu("Outbox"))
            add(Menu("Trash"))
            add(Menu("Spam"))
        }
        adapterTimeLine.notifyDataSetChanged()
    }

    private fun initAdapter() {
        adapterTimeLine = DrawerLayoutAdapter(items)
        adapterTimeLine.onItemClicked = {
            Toast.makeText(this, items[it].textTitle, Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapterTimeLine
    }

    private fun initListeners() {
        imgAvatar.setOnClickListener {

        }
    }
}
