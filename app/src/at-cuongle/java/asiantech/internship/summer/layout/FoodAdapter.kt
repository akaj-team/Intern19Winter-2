package asiantech.internship.summer.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.list_item_grid_food.view.*

class FoodAdapter(private val itemsTitleFood: MutableList<String>, private val itemsImgFood: MutableList<Int>) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int {
        return itemsTitleFood.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid_food, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            tvFoodTitle.text = itemsTitleFood[position]
            imgFood.setImageResource(itemsImgFood[position])
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvFoodTitle: TextView = view.tvFoodTitle
    val imgFood: ImageView = view.imgFood
}
