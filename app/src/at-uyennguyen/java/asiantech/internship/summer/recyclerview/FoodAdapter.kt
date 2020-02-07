package asiantech.internship.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.R.mipmap.*

class FoodAdapter(val foods: MutableList<Food?>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount() = foods.size
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        private var txtName: TextView = itemView.findViewById(R.id.tvName)
        private var imgPicture: ImageView = itemView.findViewById(R.id.imgPicture)
        private var imgHeart: ImageView = itemView.findViewById(R.id.imgHeart)
        private var tvNumberLike: TextView = itemView.findViewById(R.id.tvNumberLike)
        private var tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private var txtNameComment: TextView = itemView.findViewById(R.id.tvNameComment)

        internal fun bindData(position: Int) {
            imgHeart.setOnClickListener { onItemClicked.invoke(adapterPosition) }
            foods[position]?.run {
                txtNameComment.text=name
                imgAvatar.setImageResource(avatar)
                imgPicture.setImageResource(picture)
                txtName.text=name
                tvNumberLike.text=numberLike.toString()
                tvDescription.text=description
                imgHeart.setImageResource(if (!like) heartred else heartblack)
            }
        }
    }
}

