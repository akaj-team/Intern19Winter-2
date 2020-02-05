package asiantech.internship.summer.recyclerview

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.activity_recylerview.*
import java.util.*


class RecyclerViewMainActivity : AppCompatActivity() {

    private val newfeeds = mutableListOf<NewFeed>()
    private var isLoading: Boolean = false
    private lateinit var adapter: NewFeedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recylerview)
        adapter = NewFeedAdapter(newfeeds)
        addData()
        showData()
        addScrollListener()
    }

    private fun randomLike(): Int {
        val rd = Random()
        return rd.nextInt(1000)
    }

    private fun getRandomBoolean(): Boolean {
        val random = Random()
        return random.nextBoolean()
    }

    private fun showData() {
        adapter.onItemClicked = {
            val isStatus = newfeeds[it].isStatus
            if (isStatus) {
                newfeeds[it].isStatus = !isStatus
                newfeeds[it].likes -= 1
            } else {
                newfeeds[it].isStatus = !isStatus
                newfeeds[it].likes += 1

            }
            adapter.notifyItemChanged(it)
        }
        recycleViewMain.layoutManager = LinearLayoutManager(this)
        recycleViewMain.adapter = adapter
    }

    private fun addData() {
        newfeeds.apply {
            add(NewFeed("Michel Jackson ", R.drawable.ic_cherry_almond_frangipane_galette, getRandomBoolean(), randomLike(), "Bake an impressive dinner party dessert with minimum fuss – these chocolate puddings, also known as chocolate fondant or lava cake, have a lovely gooey centre"))
            add(NewFeed("Bebe Rexha ", R.drawable.ic_chocola, getRandomBoolean(), randomLike(), "Using simple storecupboard and freezer ingredients, this pea and chorizo risotto makes an easy midweek meal. Garnish with crisped chorizo and grated parmesan"))
            add(NewFeed("Adam Sandler ", R.drawable.ic_fools, getRandomBoolean(), randomLike(), "A crispy pie that you can adapt for your needs, add chicken or keep it veggie. A good fail-safe for your repertoire"))
            add(NewFeed("Jack Maron ", R.drawable.ic_floating_island, getRandomBoolean(), randomLike(), "This high fibre dish counts as 3 of your 5-a-day, with a creamy and spicy curried sauce"))
            add(NewFeed("Eddie Murphy ", R.drawable.ic_cornflake_cake, getRandomBoolean(), randomLike(), "In need of a quick fix? Go Tex-Mex with these fish fajitas that'll feed four in less than 15 minutes!..."))
            add(NewFeed("John Snow ", R.drawable.ic_peach_melba_pie, getRandomBoolean(), randomLike(), "This colourful, flavour-packed pizza is a great storecupboard supper with only five ingredients"))
            add(NewFeed("Jay Witch ", R.drawable.ic_treacle_tart, getRandomBoolean(), randomLike(), "Not for risotto purists - this simple recipe has just a few ingredients and the stock is added all in one go"))
            add(NewFeed("Tom Bee ", R.drawable.ic_white_chocolate_cheesecake, getRandomBoolean(), randomLike(), "This southern Italian dish is made with just five ingredients - just use the best tomatoes and bacon you can find"))
            add(NewFeed("Josh Edd", R.drawable.ic_zesty_lemon_cake, getRandomBoolean(), randomLike(), "A classic combination of flavours for a comforting family meal, with just five ingredients"))
            add(NewFeed("Tony Jame", R.drawable.ic_damson_crumble, getRandomBoolean(), randomLike(), "This springtime pasta dish requires just four ingredients. The perfect, fuss-free feast with a creamy sauce and pork meatballs that's bound to be a hit..."))
        }
        newfeeds.shuffle()
        adapter.notifyDataSetChanged()
    }


    fun setLoaded() {
        isLoading = false
    }

    private fun addScrollListener() {
        recycleViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastItem == newfeeds.size - 1) {

                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            progressBar.visibility = View.INVISIBLE
                            addData()
                        }, 1000)
                        isLoading = true
                    }
                    setLoaded()

                }
            }
        })
    }

}