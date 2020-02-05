package asiantech.internship.summer.recyclerview

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.`at-nguyenha`.activity_main.*
import kotlin.random.Random

class RecyclerViewMainActivity : AppCompatActivity() {
    private var newfeeds = mutableListOf<NewFeedModel>()
    private var newfeedsSrc = mutableListOf<NewFeedModel>()
    private var adapterNewfeeds = NewFeedAdapter(newfeeds)
    private var isLoadding = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asiantech.internship.summer.R.layout.activity_main)

        insertData()
        initAdapter()
        initScrollListener()
        refeshItem()

        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapterNewfeeds
    }

    private fun refeshItem() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                newfeeds.clear()
                insertData()
                initAdapter()
                initScrollListener()
                recyclerViewMain.adapter = adapterNewfeeds
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

                if (!isLoadding) {
                    if (lastVisibleItem == newfeeds.size - 1) {
                        progressBar1.visibility = View.VISIBLE

                        Handler().postDelayed({
                            insertData()
                            adapterNewfeeds.notifyDataSetChanged()
                            setLoading()
                        }, 2000)
                    }
                }
            }
        })

    }

    private fun setLoading() {
        isLoadding = false
        progressBar1.visibility = View.INVISIBLE
    }


    private fun initAdapter() {
        adapterNewfeeds.onItemClicked = {
            val isHeart: Boolean = newfeeds[it].isHeart
            if (isHeart) {
                newfeeds[it].isHeart = !isHeart
                newfeeds[it].likeNumber--
            } else {
                newfeeds[it].isHeart = !isHeart
                newfeeds[it].likeNumber++
            }
            adapterNewfeeds.notifyDataSetChanged()

        }
    }

    private fun initData() {
        newfeedsSrc.add(NewFeedModel("Nguyen", asiantech.internship.summer.R.drawable.ic_pizza, false, Random.nextInt(50), "this is the hamburger that I make, feel so good "))
        newfeedsSrc.add(NewFeedModel("Cuong", asiantech.internship.summer.R.drawable.ic_sashimi, false, Random.nextInt(50), "this is the hamburger that I make, feel so good "))
        newfeedsSrc.add(NewFeedModel("Uyen", asiantech.internship.summer.R.drawable.ic_hamburger, false, Random.nextInt(50), "ou can never buy love… but still you have to pay for it. ..."))
        newfeedsSrc.add(NewFeedModel("Hau", asiantech.internship.summer.R.drawable.ic_sashimi, false, Random.nextInt(50), "this is the hamburger that I make, feel so good "))
        newfeedsSrc.add(NewFeedModel("Nguyen", asiantech.internship.summer.R.drawable.ic_pizza, false, Random.nextInt(50), "Life is short. Time is fast. No reply. No rewind. moment as it comes…"))
        newfeedsSrc.add(NewFeedModel("Cuong", asiantech.internship.summer.R.drawable.ic_hamburger, false, Random.nextInt(50), "this is the hamburger that I make, feel so good "))
        newfeedsSrc.add(NewFeedModel("Hau", asiantech.internship.summer.R.drawable.ic_hamburger, false, Random.nextInt(50), "this is the hamburger that I make, feel so good "))
        newfeedsSrc.add(NewFeedModel("Nguyen", asiantech.internship.summer.R.drawable.ic_hamburger, false, Random.nextInt(50), "I am single because god is busy writing to best love story for me. "))
        newfeedsSrc.add(NewFeedModel("Uyen", asiantech.internship.summer.R.drawable.ic_pizza, false, Random.nextInt(50), "this is the hamburger that I make, feel so good "))
        newfeedsSrc.add(NewFeedModel("Cuong", asiantech.internship.summer.R.drawable.ic_sashimi, false, Random.nextInt(50), "You may only be one person to the world but  the world to one person. ... "))
    }

    private fun insertData() {
        initData()
        newfeedsSrc.shuffle()
        newfeeds.addAll(newfeedsSrc)
        newfeedsSrc.clear()
    }


}