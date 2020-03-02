package asiantech.internship.summer.layout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_gallery.*

class GalleryFragment : Fragment() {
    companion object {
        private const val COLUMN_ITEMS = 3
    }

    private lateinit var galleryAdapter: GalleryAdapter
    private var imagePath = mutableListOf<Gallery>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
        initData()
    }

    private fun initAdapter() {
        recyclerViewGallery.layoutManager = GridLayoutManager(context, COLUMN_ITEMS)
        galleryAdapter = GalleryAdapter(imagePath)
        recyclerViewGallery.adapter = galleryAdapter
    }

    private fun initData() {
        imagePath.addAll(GalleryUtils.getImage(requireContext()))
    }

    private fun initListeners() {
        galleryAdapter.onImageClicked = {
            (activity as? LayoutMainActivity)?.replaceFragment(SignUpFragment.newInstance(imagePath[it].uri))
        }
    }
}
