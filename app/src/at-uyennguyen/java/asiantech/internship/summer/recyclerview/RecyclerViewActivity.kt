package asiantech.internship.summer.recyclerview
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_recyclerview.*
import retrofit2.Call
import retrofit2.Response
class RecyclerViewActivity : AppCompatActivity() {
    companion object{
        const val DELAY_TIME = 2000
    }
    private var listNewFeed = mutableListOf<NewFeed>()
    private var listLike = mutableListOf<Like>()
    private var listUser = mutableListOf<User>()
    private var adapterRecyclerView: RecyclerViewAdapter = RecyclerViewAdapter(listNewFeed, listLike, listUser)
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        initAdapter()
        initData()
//        initListener()
    }
    private fun initAdapter() {
        var isLikeHeart = false
//        adapterRecyclerView.onItemClicked = {
//            foods[it]?.run {
//                isLikeHeart = like
//                if (isLikeHeart) {
//                    like = !isLikeHeart
//                    apply {
//                        this.numberLike++
//                    }
//                } else {
//                    like = !isLikeHeart
//                    apply {
//                        this.numberLike--
//                    }
//                }
//            }
//            adapterRecyclerView.notifyItemChanged(it, null)
//            (recyclerviewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
//        }
        recyclerviewMain.adapter = adapterRecyclerView
    }
    //    private fun initListener() {
//        recyclerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
//                if (!isLoading) {
//                    if (lastItem == foods.size - 1) {
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
//        swipeRefreshLayout.setOnRefreshListener {
//            Handler().postDelayed({
//                foods.clear()
//                initData()
//                adapterRecyclerView.notifyDataSetChanged()
//                swipeRefreshLayout.isRefreshing = false
//            }, DELAY_TIME.toLong())
//        }
//    }
    private fun initData() {
        val service = RetrofitClient.getRetrofitInstance()?.create(GetDataService::class.java)
        val callNewFeed = service?.getNewFeeds()
        val callLikes = service?.getLikes()
        val callUser = service?.getUser()
        callNewFeed?.enqueue(object : retrofit2.Callback<MutableList<NewFeed>>{
            override fun onFailure(call: Call<MutableList<NewFeed>>, t: Throwable) {
                Toast.makeText(parent.baseContext,"Please try again", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MutableList<NewFeed>>, response: Response<MutableList<NewFeed>>) {
                listNewFeed = response.body()!!
                Log.d("XXX", listNewFeed.toString())
                adapterRecyclerView.notifyDataSetChanged()
            }
        })
        callLikes?.enqueue(object : retrofit2.Callback<MutableList<Like>>{
            override fun onFailure(call: Call<MutableList<Like>>, t: Throwable) {
                Toast.makeText(parent.baseContext,"Please try again", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MutableList<Like>>, response: Response<MutableList<Like>>) {
                listLike = response.body()!!
                adapterRecyclerView.notifyDataSetChanged()
            }
        })
        callUser?.enqueue(object : retrofit2.Callback<MutableList<User>>{
            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Toast.makeText(parent.baseContext,"Please try again", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MutableList<User>>, response: Response<MutableList<User>>) {
                listUser = response.body()!!
                adapterRecyclerView.notifyDataSetChanged()
            }
        })

    }
}