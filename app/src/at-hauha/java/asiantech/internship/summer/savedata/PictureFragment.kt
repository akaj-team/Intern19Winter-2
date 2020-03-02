package asiantech.internship.summer.savedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.PictureAdapter
import asiantech.internship.summer.savedata.model.Picture
import kotlinx.android.synthetic.`at-hauha`.fragment_image.*


class PictureFragment : Fragment() {

    companion object {
        private const val COLUMN = 3
    }

    private var adapter: PictureAdapter? = null
    private var pictureList = mutableListOf<Picture>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        pictureList.addAll(Utils.getImage(requireContext()))
        adapter = PictureAdapter(pictureList)
        adapter?.onItemClick = {
            (activity as? TodoActivity)?.replaceRegisterFragment(pictureList[it].path)
        }
        recyclerViewImage.layoutManager = GridLayoutManager(requireContext(), COLUMN)
        recyclerViewImage.adapter = adapter
    }


}
