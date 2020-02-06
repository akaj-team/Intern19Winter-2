package asiantech.internship.summer.recyclerview

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.`at-nguyenha`.activity_main.*
import kotlin.random.Random

class RecyclerViewMainActivity : AppCompatActivity() {
    private var newfeeds = mutableListOf<NewFeedModel>()
    private var adapterNewFeeds = NewFeedAdapter(newfeeds)
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asiantech.internship.summer.R.layout.activity_main)

        initView()
        initAdapter()
        initData()
        initListener()
        initScrollListener()
        initRefreshItem()
    }

    private fun initView() {
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapter() {
        recyclerViewMain.adapter = adapterNewFeeds
    }

    private fun initRefreshItem() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                newfeeds.clear()
                initData()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, 2000)
        }
    }

    private fun initScrollListener() {
        recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == newfeeds.size - 1) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            initData()
                            adapterNewFeeds.notifyDataSetChanged()
                            setLoading()
                        }, 2000)
                    }
                }
            }
        })
    }

    private fun setLoading() {
        isLoading = false
        progressBar.visibility = View.INVISIBLE
    }

    private fun initListener() {
        adapterNewFeeds.onItemClicked = {
            val isHeart: Boolean = newfeeds[it].isHeart
            if (isHeart) {
                newfeeds[it].isHeart = !isHeart
                newfeeds[it].likeNumber--
            } else {
                newfeeds[it].isHeart = !isHeart
                newfeeds[it].likeNumber++
            }
            adapterNewFeeds.notifyItemChanged(it, null)
            (recyclerViewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun initData() {
        newfeeds.add(NewFeedModel("Nguyen",
                asiantech.internship.summer.R.drawable.ic_pizza,
                false, Random.nextInt(1, 1000),
                "this is the hamburger that I make, feel so good ")
        )
        newfeeds.add(NewFeedModel(
                "Cuong", asiantech.internship.summer.R.drawable.ic_sashimi,
                false, Random.nextInt(1, 1000),
                "this is the hamburger that I make, feel so good ")
        )
        newfeeds.add(NewFeedModel("Uyen",
                asiantech.internship.summer.R.drawable.ic_hamburger,
                false, Random.nextInt(1, 1000),
                "ou can never buy love… but still you have to pay for it. ..."))
        newfeeds.add(NewFeedModel("Hau",
                asiantech.internship.summer.R.drawable.ic_pizza,
                false, Random.nextInt(1, 1000),
                "this is the hamburger that I make, feel so good "))
        newfeeds.add(NewFeedModel("Nguyen",
                asiantech.internship.summer.R.drawable.ic_pizza,
                false, Random.nextInt(1, 1000),
                "Life is short. Time is fast. No reply. No rewind. moment as it comes…")
        )
        newfeeds.add(NewFeedModel("Cuong",
                asiantech.internship.summer.R.drawable.ic_sashimi,
                false, Random.nextInt(1, 1000),
                "this is the hamburger that I make, feel so good ")
        )
        newfeeds.add(NewFeedModel("Hau",
                asiantech.internship.summer.R.drawable.ic_hamburger,
                false, Random.nextInt(1, 1000),
                "this is the hamburger that I make, feel so good ")
        )
        newfeeds.add(NewFeedModel("Nguyen",
                asiantech.internship.summer.R.drawable.ic_pizza,
                false, Random.nextInt(1, 1000),
                "I am single because god is busy writing to best love story for me. ")
        )
        newfeeds.add(NewFeedModel("Uyen",
                asiantech.internship.summer.R.drawable.ic_sashimi,
                false, Random.nextInt(1, 1000),
                "this is the hamburger that I make, feel so good ")
        )
        newfeeds.add(NewFeedModel("Cuong",
                asiantech.internship.summer.R.drawable.ic_hamburger,
                false, Random.nextInt(1, 1000),
                "You may only be one person to the world but  the world to one person. ... ")
        )
    }
}
