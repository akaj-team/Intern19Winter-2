package asiantech.internship.summer.recyclerview
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_recyclerview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class RecyclerViewActivity : AppCompatActivity() {
    companion object{
        const val DELAY_TIME = 2000
    }
    private var listNewFeed = mutableListOf<NewFeed>()
    private lateinit var adapterRecyclerView: RecyclerViewAdapter
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        initAdapter()
        initData()
        initListener()
    }
    private fun initAdapter() {
        adapterRecyclerView = RecyclerViewAdapter(listNewFeed)
        recyclerviewMain.adapter = adapterRecyclerView
        var isLikeHeart = false
        adapterRecyclerView.onItemClicked = {
            listNewFeed[it].run {
                isLikeHeart = isLike
                if (isLikeHeart) {
                    isLike = !isLikeHeart
                    apply {
                        this.numberLike++
                    }
                } else {
                    isLike = !isLikeHeart
                    apply {
                        this.numberLike--
                    }
                }
            }
            updateLike(listNewFeed[it].id, listNewFeed[it])
            adapterRecyclerView.notifyItemChanged(it, null)
            (recyclerviewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        adapterRecyclerView.onItemClickedToDelete = {
            deleteNewfeed(listNewFeed[it].id)
            listNewFeed.removeAt(it)
            adapterRecyclerView.notifyDataSetChanged()
        }
    }
        private fun initListener() {
//        recyclerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
//                if (!isLoading) {
//                    if (lastItem == listNewFeed.size - 1) {
//                        progressbar.visibility = View.VISIBLE
//                        Handler().postDelayed({
//                            initData()
//                            isLoading = false
//                            progressbar.visibility = View.INVISIBLE
//                        }, 2000)
//                    }
//                }
//            }
//        })
        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                listNewFeed.clear()
                initData()
                adapterRecyclerView.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }, DELAY_TIME.toLong())
        }
    }
    private fun initData() {
        val service = RetrofitClient.getRetrofitInstance()?.create(GetDataService::class.java)
        val callNewFeed = service?.getNewFeeds()
        callNewFeed?.enqueue(object : retrofit2.Callback<MutableList<NewFeed>>{
            override fun onFailure(call: Call<MutableList<NewFeed>>, t: Throwable) {
                Toast.makeText(parent.baseContext,"Please try again", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MutableList<NewFeed>>, response: Response<MutableList<NewFeed>>) {
                response.body().let {
                    it?.let { it1 -> listNewFeed.addAll(it1) }
                }
                adapterRecyclerView.notifyDataSetChanged()
            }
        })
    }

    private fun updateLike(id : Int, newFeed: NewFeed){
        val service = RetrofitClient.getRetrofitInstance()?.create(GetDataService::class.java)
        val callNewFeed = service?.updateNewFeed(id, newFeed)
        callNewFeed?.enqueue(object : retrofit2.Callback<NewFeed>{
            override fun onFailure(call: Call<NewFeed>, t: Throwable) {
                Toast.makeText(parent.baseContext,"Please try again", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<NewFeed>, response: Response<NewFeed>) {
                adapterRecyclerView.notifyDataSetChanged()
            }

        })
    }

    private fun deleteNewfeed(id : Int){nn 
        val service = RetrofitClient.getRetrofitInstance()?.create(GetDataService::class.java)
        val callNewFeed = service?.deleteNewFeed(id)
        callNewFeed?.enqueue(object : Callback<NewFeed>{
            override fun onFailure(call: Call<NewFeed>, t: Throwable) {
                Toast.makeText(parent.baseContext,"Please try again", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<NewFeed>, response: Response<NewFeed>) {
                adapterRecyclerView.notifyDataSetChanged()
            }

        })
    }
}