package asiantech.internship.summer.recyclerview

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.activity_recylerview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NewFeedActivity : AppCompatActivity() {

    private var newFeeds = mutableListOf<NewFeed>()
    private var isLoading: Boolean = false
    private lateinit var adapter: NewFeedAdapter

    companion object {
        private const val DELAY_TIME: Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recylerview)
        initData()
        initListener()
        imgAdd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.newfeedContainer,NewFeedFragment(),null)
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun initRefresh() {
        itemRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemRefresh.setColorSchemeColors(Color.WHITE)
        itemRefresh.setOnRefreshListener {
            newFeeds.clear()
            initData()

            itemRefresh.isRefreshing = false
        }
    }

    private fun initAdapter() {
        this.adapter = NewFeedAdapter(newFeeds)
        adapter.onItemClicked = {
            val isStatus = newFeeds[it].isStatus
            if (isStatus) {
                newFeeds[it].isStatus = !isStatus
                newFeeds[it].like -= 1
                updateData(newFeeds[it].id, newFeeds[it])
            } else {
                newFeeds[it].isStatus = !isStatus
                newFeeds[it].like += 1
                updateData(newFeeds[it].id, newFeeds[it])
            }
            adapter.notifyItemChanged(it)
        }
        adapter.onImageItemClicked = {
            deleteData(newFeeds[it].id)
            newFeeds.removeAt(it)
            adapter.notifyItemChanged(it)
        }
        (recycleViewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycleViewMain.layoutManager = LinearLayoutManager(this)
        recycleViewMain.adapter = adapter
    }

    private fun initData() {
        val serviceAPI = APIRetrofit.getRetrofitInstance()?.create(APIService::class.java)
        val call = serviceAPI?.getNewfeed()
        call?.enqueue(object : Callback<List<NewFeed>> {
            override fun onFailure(call: Call<List<NewFeed>>, t: Throwable) {
                Log.i("TAG11", t.message)
            }

            override fun onResponse(call: Call<List<NewFeed>>, response: Response<List<NewFeed>>) {
                response.body()?.let {
                    newFeeds.addAll(it)
                }
                initAdapter()
            }
        })
    }

    private fun updateData(id: Int, newFeed: NewFeed) {
        val serviceAPI = APIRetrofit.getRetrofitInstance()?.create(APIService::class.java)
        val call = serviceAPI?.updateNewFeed(id, newFeed)
        call?.enqueue(object : Callback<NewFeed> {
            override fun onFailure(call: Call<NewFeed>, t: Throwable) {
                Log.i("TAG11", t.message)
            }

            override fun onResponse(call: Call<NewFeed>, response: Response<NewFeed>) {
                Log.i("TAG11", response.message())
            }

        })
    }

    private fun deleteData(id: Int) {
        val serviceAPI = APIRetrofit.getRetrofitInstance()?.create(APIService::class.java)
        serviceAPI?.deleteNewFeed(id)?.enqueue(object : Callback<NewFeed> {
            override fun onFailure(call: Call<NewFeed>, t: Throwable) {
                Log.i("TAG11", t.message)
            }

            override fun onResponse(call: Call<NewFeed>, response: Response<NewFeed>) {
                Log.i("TAG11", response.message())
            }

        })
    }

    private fun initListener() {
        initRefresh()
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
                        }, DELAY_TIME)
                        isLoading = true
                    }
                    isLoading = false
                }
            }
        })
    }

}

