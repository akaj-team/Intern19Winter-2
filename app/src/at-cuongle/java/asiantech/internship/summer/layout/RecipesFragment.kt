package asiantech.internship.summer.layout


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_recipes.*


open class RecipesFragment : Fragment() {
    private val titleFood: MutableList<String> = ArrayList()
    private val imageFood: MutableList<Int> = ArrayList()

    companion object {
        private const val COLUMN_ITEMS = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFood()
        rvFoodList.layoutManager = GridLayoutManager(context, COLUMN_ITEMS)
        rvFoodList.adapter = FoodAdapter(titleFood, imageFood)
        Log.i("XXX", "Size:" + titleFood.size.toString())
    }

    private fun addFood() {
        titleFood.apply {
            add("Apple")
            add("Pizza")
            add("Fruit")
            add("Hot Dog")
            add("Hamburger")
            add("Pasta")
            add("Chicken")
            add("Rice")
        }
        imageFood.apply {
            add(R.drawable.ic_apple)
            add(R.drawable.ic_pizza)
            add(R.drawable.ic_fruit)
            add(R.drawable.ic_hotdog)
            add(R.drawable.ic_humberger)
            add(R.drawable.ic_passta)
            add(R.drawable.ic_chicken)
            add(R.drawable.ic_rice)
        }
    }
}
