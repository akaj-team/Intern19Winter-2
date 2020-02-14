package asiantech.internship.summer.viewpager


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.fragment_home.*

class ViewHomeFragment : Fragment() {

    companion object {
        private const val KEY_POSITION = "position"
        fun newInstance(position: Int): ViewHomeFragment {
            val fragment = ViewHomeFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_POSITION, position)
            }
            return fragment
        }
    }

    private var position = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = arguments?.getInt(KEY_POSITION) ?: 0
        when (position) {
            0 -> imgContent.setImageResource(R.drawable.ic_ironman)
            1 -> imgContent.setImageResource(R.drawable.ic_thor)
            2 -> imgContent.setImageResource(R.drawable.ic_captain)
        }
    }
}
