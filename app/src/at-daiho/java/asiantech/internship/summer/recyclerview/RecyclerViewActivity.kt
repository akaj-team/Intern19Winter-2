package asiantech.internship.summer.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import com.thedeanda.lorem.LoremIpsum
import kotlinx.android.synthetic.`at-daiho`.activity_recycler_view.*
import kotlin.random.Random

class RecyclerViewActivity : AppCompatActivity() {

    private val feeds = mutableListOf<Feed>()
    private val lorem = LoremIpsum()
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initAdapter()
        initActionForFavotiteButton()
        feeds.addAll(get10FeedData())
        reloadData()
    }

    private fun initAdapter() {
        adapter = FeedAdapter(feeds)
        recycleViewMain.layoutManager = LinearLayoutManager(this)
        recycleViewMain.adapter = adapter
    }

    private fun initActionForFavotiteButton() {
        adapter.onItemClicked = {
            val isLike = feeds[it].isLike
            val feed = feeds[it]
            if (isLike) {
                feed.isLike = !isLike
                feed.noOfLikes -= 1
            } else {
                feed.isLike = !isLike
                feed.noOfLikes += 1
            }
            adapter.notifyItemChanged(it)
        }
    }

    private fun get10FeedData(): MutableList<Feed> {
        val ret = mutableListOf<Feed>()
        val images = arrayOf(R.mipmap.img_feed1,
                R.mipmap.img_feed2,
                R.mipmap.img_feed3,
                R.mipmap.img_feed4,
                R.mipmap.img_feed5,
                R.mipmap.img_feed6,
                R.mipmap.img_feed7,
                R.mipmap.img_feed8,
                R.mipmap.img_feed9,
                R.mipmap.img_feed10)
        for (i in 0..10) {
            ret.add(Feed(lorem.name,
                    images[Random.nextInt(1, 10)],
                    Random.nextBoolean(),
                    Random.nextInt(0, 10000),
                    lorem.name,
                    lorem.getWords(20,100)))
        }
        return ret
    }

    private fun reloadData() {
        adapter.notifyDataSetChanged()
    }
}
