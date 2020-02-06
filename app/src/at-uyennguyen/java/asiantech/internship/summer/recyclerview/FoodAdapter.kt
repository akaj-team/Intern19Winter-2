package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R

class FoodAdapter(val foods: MutableList<Food?>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_food_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = foods.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar: ImageView = itemView.findViewById(R.id.youravatar)
        val txtName: TextView = itemView.findViewById(R.id.name)
        val imgPictue: ImageView = itemView.findViewById(R.id.picture)
        val imgHeart: ImageView = itemView.findViewById(R.id.heart)
        var numberlike: TextView = itemView.findViewById(R.id.numberlike)
        val description: TextView = itemView.findViewById(R.id.description)
        val txtNameComment: TextView = itemView.findViewById(R.id.namecomment)

        fun bindData(position: Int) {
            imgHeart.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onItemClicked.invoke(adapterPosition)
                }
            })
            txtNameComment.setText(foods[position]?.name)
            foods[position]?.apply {
                imgAvatar.setImageResource(this.avatar)
            }
            foods[position]?.apply {
                imgPictue.setImageResource(this.avatar)
            }
            txtName.setText(foods[position]?.name)
            foods[position]?.apply {
                imgAvatar.setImageResource(this.picture)
            }
            numberlike.setText(foods[position]?.numberLike.toString())
            description.setText(foods[position]?.description)
            imgHeart.setImageResource(if (foods[position]?.like == true) R.drawable.heart2 else R.drawable.heartblack)
        }
    }
}