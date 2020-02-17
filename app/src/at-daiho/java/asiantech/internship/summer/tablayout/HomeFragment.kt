package asiantech.internship.summer.tablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-daiho`.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    companion object {
        private const val KEY_POSITION = "position"
        private const val KEY_POSITION_0 = 0
        private const val KEY_POSITION_1 = 1
        private const val KEY_POSITION_2 = 2

        fun newInstance(position: Int): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_POSITION, position)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(KEY_POSITION) ?: 0
        when (position) {
            KEY_POSITION_0 -> bgImageView.setBackgroundResource(R.drawable.bg_01)
            KEY_POSITION_1 -> bgImageView.setBackgroundResource(R.drawable.bg_02)
            KEY_POSITION_2 -> bgImageView.setBackgroundResource(R.drawable.bg_03)
        }
    }
}
