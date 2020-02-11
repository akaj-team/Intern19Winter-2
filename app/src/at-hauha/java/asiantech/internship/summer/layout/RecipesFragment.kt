package asiantech.internship.summer.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_recipes.*

class RecipesFragment : Fragment() {

    companion object {
       private const val COLUMN = 2
    }
    
    private val list: List<Food> = listOf(
            Food("Rice", R.drawable.ic_rice),
            Food("Chicken", R.drawable.ic_chicken),
            Food("Cake", R.drawable.ic_cake),
            Food("Cuttle", R.drawable.ic_cuttle),
            Food("Noodle", R.drawable.ic_noodle),
            Food("Potato", R.drawable.ic_potato)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycle_view.layoutManager = GridLayoutManager(context,COLUMN)
        recycle_view.adapter = RecyclerViewAdapter(list)
    }
 
}
