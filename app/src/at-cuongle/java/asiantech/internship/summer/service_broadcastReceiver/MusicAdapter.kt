package asiantech.internship.summer.service_broadcastReceiver

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.item_recycler_music.view.*
import java.util.concurrent.TimeUnit

class MusicAdapter(private val recyclerViewHolder: MutableList<Music>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var onSongClicked: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_music, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = recyclerViewHolder.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bindData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bindData() {
            itemView.run {
                recyclerViewHolder[adapterPosition].let {
                    tvNameMusic.text = it.name
                    tvTime.text = toMin(it.duration.toLong())
                    tvArtist.text = it.artist
                    val bitmap = convertUriToBitmap(it.uri, context)
                    if (bitmap != null) {
                        imgMusic.setImageURI(it.image)
                    } else imgMusic.setImageResource(R.drawable.ic_music)
                }
            }
        }

        init {
            itemView.setOnClickListener {
                onSongClicked.invoke(adapterPosition)
            }
        }

        private fun convertUriToBitmap(path: Uri, context: Context): Bitmap? {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, path)
            val byteArray = retriever.embeddedPicture
            if (byteArray != null) {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
            return null
        }

        private fun toMin(millis: Long): String {
            return itemView.context.getString(R.string.tv_duration, TimeUnit.MILLISECONDS.toMinutes(millis),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
        }
    }
}
