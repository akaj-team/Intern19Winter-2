package asiantech.internship.summer.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import com.mooveit.library.Fakeit
import kotlinx.android.synthetic.`at-daiho`.activity_recycler_view.*
import kotlin.random.Random
import kotlin.random.nextUInt

class RecyclerViewActivity : AppCompatActivity() {

    private val feeds = mutableListOf<Feed>()
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fakeit.init()

        setContentView(R.layout.activity_recycler_view)
        adapter = FeedAdapter(feeds)
        feeds.addAll(get10FeedData())
        adapter.notifyDataSetChanged()
        reloadData()
    }

    private fun get10FeedData(): MutableList<Feed> {
        val ret = mutableListOf<Feed>()
        for (i in 0..10) {
            ret.add(Feed(Fakeit.name().nameWithMiddle(), Random.nextUInt(0.toUInt(), 10.toUInt()), Random.nextBoolean(), Random.nextUInt(0.toUInt(), 10000.toUInt()), Fakeit.lorem().words() ))
        }
        return ret
    }

    private fun reloadData() {
        recycleViewMain.layoutManager = LinearLayoutManager(this)
        recycleViewMain.adapter = adapter
    }
}
