package asiantech.internship.summer.recyclerView

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_time_line.*

class TimelineActivity : AppCompatActivity() {
    private val timelineItems = mutableListOf<TimelineItem?>()
    private var isLoading = false
    private lateinit var adapterTimeLine: TimeLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        adapterTimeLine = TimeLineAdapter(timelineItems)
        initFood()
        initAdapter()
        initScrollListener()
        pullToRefresh()
    }

    private fun initFood() {
        timelineItems.apply {
            add(TimelineItem(getString(R.string.tv_name_1), R.drawable.ic_apple, false, (1..99).random(), getString(R.string.tv_title_1)))
            add(TimelineItem(getString(R.string.tv_name_2), R.drawable.ic_pizza, true, (1..99).random(), getString(R.string.tv_title_2)))
            add(TimelineItem(getString(R.string.tv_name_3), R.drawable.ic_fruit, false, (1..99).random(), getString(R.string.tv_title_3)))
            add(TimelineItem(getString(R.string.tv_name_4), R.drawable.ic_hotdog, true, (1..99).random(), getString(R.string.tv_title_4)))
            add(TimelineItem(getString(R.string.tv_name_5), R.drawable.ic_humberger, true, (1..99).random(), getString(R.string.tv_title_5)))
            add(TimelineItem(getString(R.string.tv_name_6), R.drawable.ic_passta, true, (1..99).random(), getString(R.string.tv_title_6)))
            add(TimelineItem(getString(R.string.tv_name_7), R.drawable.ic_chicken, true, (1..99).random(), getString(R.string.tv_title_7)))
            add(TimelineItem(getString(R.string.tv_name_8), R.drawable.ic_rice, true, (1..99).random(), getString(R.string.tv_title_8)))
            add(TimelineItem(getString(R.string.tv_name_9), R.drawable.ic_salad, false, (1..99).random(), getString(R.string.tv_title_9)))
            add(TimelineItem(getString(R.string.tv_name_10), R.drawable.ic_cake, true, (1..99).random(), getString(R.string.tv_title_10)))
        }
        timelineItems.shuffle()
        adapterTimeLine.notifyDataSetChanged()
    }

    private fun initAdapter() {
        adapterTimeLine.onItemClicked = { it ->
            timelineItems[it]?.let {
                val like = it.like
                if (it.isLike) {
                    it.isLike = false
                    it.like = like - 1
                } else {
                    it.isLike = true
                    it.like = like + 1
                }
            }
            adapterTimeLine.notifyDataSetChanged()
        }
        rvFoodList.adapter = adapterTimeLine
    }

    private fun initScrollListener() {
        rvFoodList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if (!isLoading) {
                    if (lastVisibleItem == timelineItems.size - 1) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            loadMore()
                        }, 2000)
                    }
                }
                isLoading = false
            }
        })
    }

    private fun loadMore() {
        isLoading = true
        timelineItems.add(null)
        progressBar.visibility = View.INVISIBLE
        adapterTimeLine.notifyItemInserted(timelineItems.size - 1)
        initFood()
    }

    private fun pullToRefresh() {
        itemRefresh.setOnRefreshListener {
            timelineItems.clear()
            initFood()
            initAdapter()
            itemRefresh.isRefreshing = false
        }
    }
}
