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
    companion object {
        private const val DELAY_PROGRESSBAR: Long = 2000
    }

    private val timelineItems = mutableListOf<TimeLineItem>()
    private var isLoading = false
    private lateinit var adapterTimeLine: TimeLines
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        initAdapter()
        initListeners()
        initData()
    }

    private fun initData() {
        timelineItems.apply {
            add(TimeLineItem("Addison", R.drawable.ic_apple, false, (1..99).random(), "apple is really delicious"))
            add(TimeLineItem("Carter", R.drawable.ic_pizza, true, (1..99).random(), "pizza is really delicious"))
            add(TimeLineItem("Christian", R.drawable.ic_fruit, false, (1..99).random(), "fruit is really delicious"))
            add(TimeLineItem("Colton", R.drawable.ic_hotdog, true, (1..99).random(), "hotdog is really delicious"))
            add(TimeLineItem("Grayson", R.drawable.ic_humberger, true, (1..99).random(), "humberger is really delicious"))
            add(TimeLineItem("Hudson", R.drawable.ic_passta, true, (1..99).random(), "passta is really delicious"))
            add(TimeLineItem("Jack", R.drawable.ic_rice, true, (1..99).random(), "rice is really delicious"))
            add(TimeLineItem("Jaxson", R.drawable.ic_salad, false, (1..99).random(), "salad is really delicious"))
            add(TimeLineItem("Hunter", R.drawable.ic_chicken, true, (1..99).random(), "chicken is really delicious"))
            add(TimeLineItem("Landon", R.drawable.ic_cake, true, (1..99).random(), "cake is really delicious"))
        }
        timelineItems.shuffle()
        adapterTimeLine.notifyDataSetChanged()
    }

    private fun initAdapter() {
        adapterTimeLine = TimeLines(timelineItems)
        adapterTimeLine.onItemClicked = { it ->
            timelineItems[it].let {
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

    private fun initListeners() {
        itemRefresh.setOnRefreshListener {
            timelineItems.clear()
            initData()
            itemRefresh.isRefreshing = false
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
                        }, DELAY_PROGRESSBAR)
                    }
                }
                isLoading = false
            }
        })
    }

    private fun loadMore() {
        isLoading = true
        progressBar.visibility = View.INVISIBLE
        initData()
    }
}
