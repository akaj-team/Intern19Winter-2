package asiantech.internship.summer.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.service.model.Song

class MusicAdapter () : RecyclerView.Adapter<MusicAdapter.SongViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.song_item,parent,false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        (holder as? SongViewHolder)?.binData()
    }

    inner class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binData(){

        }
    }

}
