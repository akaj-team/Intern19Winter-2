package asiantech.internship.summer.recyclerView

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_time_line.*

class TimelineActivity : AppCompatActivity() {
    private val timelineItems = mutableListOf<TimelineViewHolder?>()
    private var isLoading = false
    private lateinit var adapterTimeLine: TimeLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        initAdapter()
        initListeners()
        initData()
    }

    private fun initData() {
        timelineItems.apply {
            add(TimelineViewHolder(getString(R.string.tv_name_1), R.drawable.img_apple, false, (1..99).random(), getString(R.string.tv_title_1)))
            add(TimelineViewHolder(getString(R.string.tv_name_2), R.drawable.img_pizza, true, (1..99).random(), getString(R.string.tv_title_2)))
            add(TimelineViewHolder(getString(R.string.tv_name_3), R.drawable.img_fruit, false, (1..99).random(), getString(R.string.tv_title_3)))
            add(TimelineViewHolder(getString(R.string.tv_name_4), R.drawable.img_hotdog, true, (1..99).random(), getString(R.string.tv_title_4)))
            add(TimelineViewHolder(getString(R.string.tv_name_5), R.drawable.img_humberger, true, (1..99).random(), getString(R.string.tv_title_5)))
            add(TimelineViewHolder(getString(R.string.tv_name_6), R.drawable.img_passta, true, (1..99).random(), getString(R.string.tv_title_6)))
            add(TimelineViewHolder(getString(R.string.tv_name_8), R.drawable.img_rice, true, (1..99).random(), getString(R.string.tv_title_8)))
            add(TimelineViewHolder(getString(R.string.tv_name_9), R.drawable.img_salad, false, (1..99).random(), getString(R.string.tv_title_9)))
            add(TimelineViewHolder(getString(R.string.tv_name_7), R.drawable.img_chicken, true, (1..99).random(), getString(R.string.tv_title_7)))
            add(TimelineViewHolder(getString(R.string.tv_name_10), R.drawable.img_cake, true, (1..99).random(), getString(R.string.tv_title_10)))
        }
        timelineItems.shuffle()
        adapterTimeLine.notifyDataSetChanged()
    }

    private fun initAdapter() {
        adapterTimeLine = TimeLineAdapter(timelineItems)
        adapterTimeLine.onItemClicked = { it ->
            timelineItems[it]?.let {
                if (it.isLike) {
                    it.isLike = false
                    it.like -= 1
                } else {
                    it.isLike = true
                    it.like += 1
                }
            }
            adapterTimeLine.notifyItemChanged(it, null)
            (recycleView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
        recycleView.adapter = adapterTimeLine
    }

    private fun loadMore() {
        isLoading = true
        progressBar.visibility = View.INVISIBLE
        initData()
        adapterTimeLine.notifyItemInserted(timelineItems.size - 1)
    }

    private fun initListeners() {
        itemRefresh.setOnRefreshListener {
            timelineItems.clear()
            initData()
            itemRefresh.isRefreshing = false
            adapterTimeLine.notifyDataSetChanged()
        }

        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
}
