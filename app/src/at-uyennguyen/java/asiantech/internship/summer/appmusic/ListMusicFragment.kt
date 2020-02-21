package asiantech.internship.summer.appmusic

import android.app.Activity
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_list_music.*


class ListMusicFragment : Fragment() {
    var position: Int = 0
    lateinit var listMusicAdapter: ListMusicAdapter
    private var listMedia = arrayListOf<Media>()

    companion object {
        private var READ_CODE_REQUEST = 111
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listMusicAdapter = listMedia.let { context?.let { it1 -> ListMusicAdapter(it, it1) } }!!
        if (context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), READ_CODE_REQUEST)
        } else {
            loadMedia()
        }
        listMusicAdapter.onItemClicked = {
            listMedia[it].run {
                tvBottomName.text = listMedia[it].nameSong
                tvBottomSinger.text = listMedia[it].singer
                imgBottomThumbnail.setImageURI(Uri.parse(listMedia[it].thumbnail))
                if (imgBottomThumbnail.drawable == null) {
                    imgBottomThumbnail.setImageResource(R.drawable.ic_music_note)
                }
            }
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, PlayMusicFragment.newInstance(listMedia, it, listMedia[it]))
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == READ_CODE_REQUEST && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadMedia()
        }
    }

    private fun loadMedia() {
        val songCursor = context?.contentResolver?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
        while (songCursor != null && songCursor.moveToNext()) {
            val songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val songTime = songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            var songSinger = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val albumId: Long? = songCursor.getLong(songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            val songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(sArtworkUri,
                    albumId!!)

            if (songSinger == "<unknown>") {
                songSinger = ""
            }

            val time = convertDuration(songTime).toString()
            listMedia.add(Media(songName, songSinger, songTime.toString(), albumArtUri.toString(), songPath))
        }

        listMusicAdapter = listMedia.let { context?.let { it1 -> ListMusicAdapter(it, it1) } }!!
        recyclerView.adapter = listMusicAdapter
    }

    private fun convertDuration(duration: Long): String? {
        var out: String? = null
        var hours: Long = 0
        try {
            hours = (duration / 3600000).toLong()
        } catch (e: Exception) {
            e.printStackTrace()
            return out
        }

        val remainingMinutes = (duration - hours * 3600000) / 60000
        var minutes = remainingMinutes.toString()
        if (minutes.equals(0)) {
            minutes = "00"
        }
        val remaining_seconds = duration - hours * 3600000 - remainingMinutes * 60000
        var seconds = remaining_seconds.toString()
        if (seconds.length < 2) {
            seconds = "00"
        } else {
            seconds = seconds.substring(0, 2)
        }

        if (hours > 0) {
            out = "$hours:$minutes:$seconds"
        } else {
            out = "$minutes:$seconds"
        }

        return out
    }
//    private fun initAdapter(){
//        listMusicAdapter.onItemClicked = {
//            val playMusicActivity : PlayMusicFragment = PlayMusicFragment()
//            activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.framelayout, playMusicActivity)
//                    ?.addToBackStack(null)
//                    ?.commit()
//            var musicDataIntent = Intent()
//                tvBottomSongName?.setText(nameSong)
//                tvBottomSinger?.setText(singer)
//                if (thumbnail != null) {
//                    imgBottomThumbnail?.setImageURI(thumbnail)
//                }
//                if (imgThumbnail.drawable == null) {
//                    imgBottomThumbnail?.setImageResource(R.drawable.bg_music)
//                }
//            }
//        }
}