package asiantech.internship.summer.retrofit

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.retrofit.api.ClientAPI
import asiantech.internship.summer.retrofit.api.NewFeedAPI
import kotlinx.android.synthetic.`at-nguyenha`.activity_recyclerview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewMainActivity : AppCompatActivity() {
    private var newfeeds = mutableListOf<NewFeedModel>()
    private var adapterNewFeeds = NewFeedAdapter(newfeeds)
    private var isLoading = false
    private val delayTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asiantech.internship.summer.R.layout.activity_recyclerview)
        getListAPI()
        initListener()
    }

    private fun initAdapter() {
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapterNewFeeds
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

    private fun initListener() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                newfeeds.clear()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, delayTime)
        }

        recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == newfeeds.size - 1) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            adapterNewFeeds.notifyDataSetChanged()
                            isLoading = false
                            progressBar.visibility = View.INVISIBLE
                        }, delayTime)
                    }
                }
            }
        })
    }

    private fun getListAPI() {
        val service = ClientAPI.createServiceClient()?.create(NewFeedAPI::class.java)
        val call = service?.getAllNewFeed()
        call?.enqueue(object : Callback<MutableList<NewFeedModel>> {
            override fun onFailure(call: Call<MutableList<NewFeedModel>>, t: Throwable) {
            }

            override fun onResponse(call: Call<MutableList<NewFeedModel>>, response: Response<MutableList<NewFeedModel>>) {
                response.body().let {
                    it?.let { it1 -> newfeeds.addAll(it1) }
                }
                initAdapter()
            }

        })
    }


}
