package asiantech.internship.summer.recyclerView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_time_line.*

class TimelineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        addFood()
    }

    private fun addFood() {
        val timelineItems = mutableListOf<TimelineItem>()
        timelineItems.apply {
            add(TimelineItem("Addison", R.drawable.ic_apple, false, (1..99).random(), "apple is really delicious."))
            add(TimelineItem("Carter", R.drawable.ic_pizza, true, (1..99).random(), "pizza is really delicious"))
            add(TimelineItem("Christian", R.drawable.ic_fruit, false, (1..99).random(), "fruit is really delicious"))
            add(TimelineItem("Colton", R.drawable.ic_hotdog, true, (1..99).random(), "hot dog is really delicious"))
            add(TimelineItem("Grayson", R.drawable.ic_humberger, true, (1..99).random(), "hamburger is really delicious"))
            add(TimelineItem("Hudson", R.drawable.ic_passta, true, (1..99).random(), "pasta is really delicious"))
            add(TimelineItem("Hunter", R.drawable.ic_chicken, true, (1..99).random(), "chicken is really delicious"))
            add(TimelineItem("Jack", R.drawable.ic_rice, true, (1..99).random(), "rice is really delicious"))
            add(TimelineItem("Jaxon", R.drawable.ic_salad, false, (1..99).random(), "salad is really delicious"))
            add(TimelineItem("Landon", R.drawable.ic_cake, true, (1..99).random(), "cake is really delicious"))
        }
        val adapterTimeLine = TimeLineAdapter(timelineItems)
        adapterTimeLine.onItemClicked = {
            val like = timelineItems[it].like
            if (timelineItems[it].isLike) {
                timelineItems[it].isLike = false
                timelineItems[it].like = like - 1
            } else {
                timelineItems[it].isLike = true
                timelineItems[it].like = like + 1
            }
            adapterTimeLine.notifyDataSetChanged()
        }
        rvFoodList.layoutManager = LinearLayoutManager(this)
        rvFoodList.adapter = adapterTimeLine
    }
}
