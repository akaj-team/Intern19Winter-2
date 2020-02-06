package asiantech.internship.summer.recyclerview

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.activity_recyclerview.*

class RecyclerViewActivity : AppCompatActivity() {
    private var foods = mutableListOf<Food?>()
    private var foodAdapter: FoodAdapter = FoodAdapter(foods)
    private var isLoadding = false
    private var likeHeart: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        initData()
        initListener()
        initAdapter()
        addScrollListener()
        refreshItem()
    }

    private fun initAdapter() {
        recyclerviewMain.adapter = foodAdapter
    }

    private fun initListener() {
        foodAdapter.onItemClicked = {
            foods[it]?.run {
                likeHeart = like
                if (likeHeart) {
                    like = !likeHeart
                    apply {
                        this.numberLike++
                    }
                } else {
                    like = !likeHeart
                    apply {
                        this.numberLike--
                    }
                }
            }
            foodAdapter.notifyItemChanged(it, null)
            (recyclerviewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun addScrollListener() {
        recyclerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoadding) {
                    if (lastItem == foods.size - 1) {
                        progressbar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            initData()
                            isLoadding = false
                            progressbar.visibility = View.INVISIBLE
                        }, 2000)
                    }
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
        foods.add(Food(R.mipmap.banhcuon, "Bánh cuộn", R.mipmap.banhcuon, false, 123, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.banhmy, "Bánh mỳ", R.mipmap.banhmy, false, 567, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.buncha, "Bún chả", R.mipmap.buncha, true, 19, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.chagio, "Chả giò", R.mipmap.chagio, false, 245, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.chaolong, "Cháo lòng", R.mipmap.chaolong, false, 155, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.comtron, "Cơm trộn", R.mipmap.comtron, true, 678, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.echxaoxaot, "Ếch xào xả ớt", R.mipmap.echxaoxaot, false, 90, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.garan, "Gà rán", R.mipmap.garan, false, 777, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.goicatrich, "Gỏi cá trích", R.mipmap.goicatrich, true, 185, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.mipmap.goicuon, "Gói cuốn", R.mipmap.goicuon, false, 317, "this is the hamburger that I make, feel so good "))
        foods.shuffle()
        foodAdapter.notifyDataSetChanged()
    }
}

