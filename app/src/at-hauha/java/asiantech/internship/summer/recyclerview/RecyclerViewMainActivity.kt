package asiantech.internship.summer.recyclerview

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.activity_recylerview.*
import java.util.*


class RecyclerViewMainActivity : AppCompatActivity() {

    private val newFeeds = mutableListOf<NewFeed>()
    private var isLoading: Boolean = false
    private lateinit var adapter: NewFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recylerview)
        this.adapter = NewFeedAdapter(newFeeds)
        initData()
        initView()
        initRefresh()
    }

    private fun initRefresh() {
        itemRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemRefresh.setColorSchemeColors(Color.WHITE)
        itemRefresh.setOnRefreshListener {
            newFeeds.clear()
            initData()
            initScrollListener()
            itemRefresh.isRefreshing = false
        }
    }

    private fun randomLike(): Int {
        val rd = Random()
        return rd.nextInt(1000)
    }

    private fun getRandomBoolean(): Boolean {
        val random = Random()
        return random.nextBoolean()
    }

    private fun initView() {
        adapter.onItemClicked = {
            val isStatus = newFeeds[it].isStatus
            if (isStatus) {
                newFeeds[it].isStatus = !isStatus
                newFeeds[it].likes -= 1
            } else {
                newFeeds[it].isStatus = !isStatus
                newFeeds[it].likes += 1

            }
            adapter.notifyItemChanged(it)

        }
        (recycleViewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycleViewMain.layoutManager = LinearLayoutManager(this)
        recycleViewMain.adapter = adapter
    }

    private fun initData() {
        newFeeds.apply {
            add(NewFeed(getString(R.string.username_michel), R.drawable.ic_cherry_almond_frangipane_galette, getRandomBoolean(), randomLike(),"Frangipane", getString(R.string.preview_1)))
            add(NewFeed(getString(R.string.username_beb), R.drawable.ic_chocola, getRandomBoolean(), randomLike(),"Chocola", getString(R.string.preview_2)))
            add(NewFeed(getString(R.string.username_adam), R.drawable.ic_fools, getRandomBoolean(), randomLike(),"Fools", getString(R.string.preview_3)))
            add(NewFeed(getString(R.string.username_jack), R.drawable.ic_floating_island, getRandomBoolean(), randomLike(),"Floating Island", getString(R.string.preview_4)))
            add(NewFeed(getString(R.string.username_eddie), R.drawable.ic_cornflake_cake, getRandomBoolean(), randomLike(),"Cornflake", getString(R.string.preview_5)))
            add(NewFeed(getString(R.string.username_john), R.drawable.ic_peach_melba_pie, getRandomBoolean(), randomLike(),"Peach Melba", getString(R.string.preview_6)))
            add(NewFeed(getString(R.string.username_jay), R.drawable.ic_treacle_tart, getRandomBoolean(), randomLike(),"Treacle", getString(R.string.preview_7)))
            add(NewFeed(getString(R.string.username_tom), R.drawable.ic_white_chocolate_cheesecake, getRandomBoolean(), randomLike(),"Chocolate Cheesecake", getString(R.string.preview_8)))
            add(NewFeed(getString(R.string.username_josh), R.drawable.ic_zesty_lemon_cake, getRandomBoolean(), randomLike(),"Zesty Lemon", getString(R.string.preview_9)))
            add(NewFeed(getString(R.string.username_tony), R.drawable.ic_damson_crumble, getRandomBoolean(), randomLike(),"Damson Crumble", getString(R.string.preview_10)))
        }
        newFeeds.shuffle()
        adapter.notifyDataSetChanged()
    }

    private fun setLoaded() {
        isLoading = false
    }

    private fun initScrollListener() {

        recycleViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastItem == newFeeds.size - 1) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            progressBar.visibility = View.INVISIBLE
                            initData()
                        }, 1000)
                        isLoading = true
                    }
                    setLoaded()
                }
            }
        })
    }

}
