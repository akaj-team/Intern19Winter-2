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
    companion object{
        const val DELAY_TIME = 2000
    }
    private var foods = mutableListOf<Food?>()
    private var adapterFood: FoodAdapter = FoodAdapter(foods)
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        initAdapter()
        initData()
        initListener()
    }

    private fun initAdapter() {
        var isLikeHeart = false
        adapterFood.onItemClicked = {
            foods[it]?.run {
                isLikeHeart = like
                if (isLikeHeart) {
                    like = !isLikeHeart
                    apply {
                        this.numberLike++
                    }
                } else {
                    like = !isLikeHeart
                    apply {
                        this.numberLike--
                    }
                }
            }
            adapterFood.notifyItemChanged(it, null)
            (recyclerviewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        recyclerviewMain.adapter = adapterFood
    }

    private fun initListener() {
        recyclerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastItem == foods.size - 1) {
                        progressbar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            initData()
                            isLoading = false
                            progressbar.visibility = View.INVISIBLE
                        }, 2000)
                    }
                }
            }
        })
        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                foods.clear()
                initData()
                adapterFood.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }, DELAY_TIME.toLong())
        }
    }

    private fun initData() {
        foods.add(Food(R.drawable.img_banhcuon, "Bánh cuộn", R.drawable.img_banhcuon, false, 123, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_banhmy, "Bánh mỳ", R.drawable.img_banhmy, false, 567, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_buncha, "Bún chả", R.drawable.img_buncha, true, 19, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_chagio, "Chả giò", R.drawable.img_chagio, false, 245, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_chaolong, "Cháo lòng", R.drawable.img_chaolong, false, 155, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_comtron, "Cơm trộn", R.drawable.img_comtron, true, 678, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_echxaoxaot, "Ếch xào xả ớt", R.drawable.img_echxaoxaot, false, 90, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_garan, "Gà rán", R.drawable.img_garan, false, 777, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_goicatrich, "Gỏi cá trích", R.drawable.img_goicatrich, true, 185, "this is the hamburger that I make, feel so good "))
        foods.add(Food(R.drawable.img_goicuon, "Gói cuốn", R.drawable.img_goicuon, false, 317, "this is the hamburger that I make, feel so good "))
        foods.shuffle()
        adapterFood.notifyDataSetChanged()
    }
}

