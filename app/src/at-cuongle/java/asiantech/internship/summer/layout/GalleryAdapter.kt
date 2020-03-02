package asiantech.internship.summer.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import com.bumptech.glide.Glide

class GalleryAdapter(private var gallery: MutableList<Gallery>) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    internal var onImageClicked: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int = gallery.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        (holder as? GalleryViewHolder)?.bindData()
    }

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgGallery = itemView.findViewById<ImageView>(R.id.imgGallery)

        init {
            imgGallery.setOnClickListener {
                onImageClicked.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            gallery[adapterPosition].let {
                Glide.with(itemView).load(it.uri).into(imgGallery)
            }
        }
    }
}
