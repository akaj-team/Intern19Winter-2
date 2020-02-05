package asiantech.internship.summer.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class RecyclerViewAdapter(private val list: List<Food>): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_food,parent,false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item  = list[position]
        holder.imgFood.setImageResource(item.nImage)
        holder.txtFoodName.text = item.nName
    }

    override fun getItemCount(): Int = list.size

     class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgFood : ImageView = itemView.findViewById(R.id.imgFood)
        val txtFoodName : TextView = itemView.findViewById(R.id.txtFoodName)
    }
}