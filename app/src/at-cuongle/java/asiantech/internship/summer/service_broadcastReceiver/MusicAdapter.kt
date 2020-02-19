package asiantech.internship.summer.service_broadcastReceiver

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_list_music.view.*
import kotlinx.android.synthetic.`at-cuongle`.item_recycler_music.view.*
import java.util.concurrent.TimeUnit

class MusicAdapter(private val recyclerViewHolder: MutableList<Music>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var onSongClicked: (position: Int) -> Unit = {}
    internal var onButtonClicked: (position: Int) -> Unit = {}
    private lateinit var mediaPlayer: MediaPlayer
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
                    imgMusic.setImageURI(it.image)
                }
            }
        }

        init {
            itemView.setOnClickListener {
                onSongClicked.invoke(adapterPosition)

//                if (mediaPlayer.isPlaying){
//                    mediaPlayer.release()
//                }
            }
//            itemView.btnPP.setOnClickListener {
//                onButtonClicked.invoke(adapterPosition)
//            }
        }


        private fun toMin(millis: Long): String {
            return itemView.context.getString(R.string.tv_duration, TimeUnit.MILLISECONDS.toMinutes(millis),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
        }
    }
}
