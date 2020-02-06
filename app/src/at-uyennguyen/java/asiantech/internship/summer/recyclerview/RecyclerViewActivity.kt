package asiantech.internship.summer.recyclerview

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_recyclerview.*

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var foods = mutableListOf<Food?>()
    private lateinit var foodAdapter: FoodAdapter
    private var isLoadding = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        foodAdapter = FoodAdapter(foods)
        initData()
        initAdapter()
        recyclerView = findViewById(R.id.recyclerviewMain)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = foodAdapter
        addScrollListener()
        refreshItem()
    }

    private fun initAdapter() {
        foodAdapter.onItemClicked = {

            var likeHeart = false
            foods[it]?.apply {
                likeHeart = this.like
            }
            if (likeHeart) {
                foods[it]?.like = !likeHeart
                foods[it]?.apply {
                    this.numberLike--
                }
            } else {
                foods[it]?.like = !likeHeart
                foods[it]?.apply {
                    this.numberLike++
                }
            }
            foodAdapter.notifyDataSetChanged()
        }
//        recyclerView.layoutManager=LinearLayoutManager(this)
//        recyclerView.adapter= foodAdapter
    }

    private fun addScrollListener() {
        recyclerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoadding) {
                    Log.d("aaa", lastItem.toString())
                    Log.d("aaa", foods.size.toString())
                    if (lastItem == foods.size - 1) {
                        progressbar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            progressbar.visibility = View.INVISIBLE
                            initData()
                        }, 2000)
                        isLoadding = true
                    }
                    isLoadding = false
                }
            }
        })
    }

    private fun refreshItem() {
        swiperefreshlayout.setOnRefreshListener {
            Handler().postDelayed({
                foods.clear()
                initData()
                addScrollListener()
                swiperefreshlayout.isRefreshing = false
            }, 2000)
        }
    }

    private fun initData() {
        foods.add(Food(R.drawable.banhcuon, "Bánh cuộn", R.drawable.banhcuon, false, 123, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.banhmy, "Bánh mỳ", R.drawable.banhmy, false, 567, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.buncha, "Bún chả", R.drawable.buncha, true, 19, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.chagio, "Chả giò", R.drawable.chagio, false, 245, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.chaolong, "Cháo lòng", R.drawable.chaolong, false, 155, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.comtron, "Cơm trộn", R.drawable.comtron, true, 678, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.echxaoxaot, "Ếch xào xả ớt", R.drawable.echxaoxaot, false, 90, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.garan, "Gà rán", R.drawable.garan, false, 777, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.goicatrich, "Gỏi cá trích", R.drawable.goicatrich, true, 185, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.goicuon, "Gói cuốn", R.drawable.goicuon, false, 317, "this is the hamburger that I make, feel so good "))
        foods.shuffle()
        foodAdapter.notifyDataSetChanged()
    }
}

