package asiantech.internship.summer.recyclerview

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.activity_recylerview.*
import java.util.*

class RecyclerViewMainActivity : AppCompatActivity() {

    private val newFeeds = mutableListOf<NewFeed>()
    private var isLoading: Boolean = false
    private lateinit var adapter: NewFeedAdapter

    companion object {
        private const val DELAY_TIME: Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recylerview)
        initAdapter()
        initData()
        initListener()
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

    private fun randomLike(): Int {
        val rd = Random()
        return rd.nextInt(1000)
    }

    private fun getRandomBoolean(): Boolean {
        val random = Random()
        return random.nextBoolean()
    }

    private fun initAdapter() {
        this.adapter = NewFeedAdapter(newFeeds)
        adapter.onItemClicked = {
            val isStatus = newFeeds[it].isStatus
            if (isStatus) {
                newFeeds[it].isStatus = !isStatus
                newFeeds[it].likes -= 1
            } else {
                newFeeds[it].isStatus = !isStatus
                newFeeds[it].likes += 1

            }
            adapter.notifyItemChanged(it)

        }
        (recycleViewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycleViewMain.layoutManager = LinearLayoutManager(this)
        recycleViewMain.adapter = adapter
    }

    private fun initData() {
        newFeeds.apply {
            add(NewFeed("Michel Jackson", R.drawable.ic_cherry_almond_frangipane_galette, getRandomBoolean(), randomLike(), "Frangipane", "Bake an impressive dinner party dessert with minimum fuss – these chocolate puddings, also known as chocolate fondant or lava cake, have a lovely gooey centre"))
            add(NewFeed("Bebe Rexha", R.drawable.ic_chocola, getRandomBoolean(), randomLike(), "Chocola", "Using simple storecupboard and freezer ingredients, this pea and chorizo risotto makes an easy midweek meal. Garnish with crisped chorizo and grated parmesan"))
            add(NewFeed("Adam Sandler", R.drawable.ic_fools, getRandomBoolean(), randomLike(), "Fools", "A crispy pie that you can adapt for your needs, add chicken or keep it veggie. A good fail-safe for your repertoire"))
            add(NewFeed("Jack Maron", R.drawable.ic_floating_island, getRandomBoolean(), randomLike(), "Floating Island", "This high fibre dish counts as 3 of your 5-a-day, with a creamy and spicy curried sauce"))
            add(NewFeed("Eddie Murphy", R.drawable.ic_cornflake_cake, getRandomBoolean(), randomLike(), "Cornflake", "In need of a quick fix? Go Tex-Mex with these fish fajitas that'll feed four in less than 15 minutes!..."))
            add(NewFeed("John Snow", R.drawable.ic_peach_melba_pie, getRandomBoolean(), randomLike(), "Peach Melba", "This colourful, flavour-packed pizza is a great storecupboard supper with only five ingredients"))
            add(NewFeed("Jay Witch", R.drawable.ic_treacle_tart, getRandomBoolean(), randomLike(), "Treacle", "Not for risotto purists - this simple recipe has just a few ingredients and the stock is added all in one go"))
            add(NewFeed("Tom Stiller", R.drawable.ic_white_chocolate_cheesecake, getRandomBoolean(), randomLike(), "Chocolate Cheesecake", "This southern Italian dish is made with just five ingredients - just use the best tomatoes and bacon you can find"))
            add(NewFeed("Josh Edd", R.drawable.ic_zesty_lemon_cake, getRandomBoolean(), randomLike(), "Zesty Lemon", "A classic combination of flavours for a comforting family meal, with just five ingredients"))
            add(NewFeed("Jame Corner", R.drawable.ic_damson_crumble, getRandomBoolean(), randomLike(), "Damson Crumble", "This springtime pasta dish requires just four ingredients. The perfect, fuss-free feast with a creamy sauce and pork meatballs that's bound to be a hit..."))
        }
        newFeeds.shuffle()
        adapter.notifyDataSetChanged()
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
