package asiantech.internship.summer.recyclerview
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
class RecyclerViewAdapter(val newFeed: MutableList<NewFeed>, val like: MutableList<Like>, val user: MutableList<User>) : RecyclerView.Adapter<RecyclerViewAdapter.FoodViewHolder>() {
    internal var onItemClicked: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.newfeed_item, parent, false)
        return FoodViewHolder(view)
    }
    override fun getItemCount() = newFeed.size
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }
    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        private var tvName: TextView = itemView.findViewById(R.id.tvName)
        private var imgPicture: ImageView = itemView.findViewById(R.id.imgPicture)
        private var imgHeart: ImageView = itemView.findViewById(R.id.imgHeart)
        private var tvNumberLike: TextView = itemView.findViewById(R.id.tvNumberLike)
        private var tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private var tvNameComment: TextView = itemView.findViewById(R.id.tvNameComment)
        internal fun bindData(position: Int) {
            Log.d("AAA", newFeed[0].status)
            imgHeart.setOnClickListener { onItemClicked.invoke(adapterPosition) }
            newFeed[position].run {
                imgPicture.setImageResource(picture.toInt())
                tvDescription.text = status
//                imgHeart.setImageResource(if (!like) img_heartred else img_heartblack)
            }
            like[position].run {
                tvNumberLike.text = numberLike.toString()
//                imgHeart.setImageResource(if (!like) img_heartred else img_heartblack)
            }
            user[position].run {
                tvNameComment.text = nameUser
                imgAvatar.setImageResource(avatar.toInt())
                tvName.text = nameUser
//                imgHeart.setImageResource(if (!like) img_heartred else img_heartblack)
            }
        }
    }
}