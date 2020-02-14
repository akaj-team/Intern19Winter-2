package asiantech.internship.summer.viewPagerAndTagLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_home.*

class HomeFragment : Fragment() {

    private var position = 0

    companion object {
        const val POSITION = "position"
        fun newInstance(position: Int): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = Bundle().apply {
                putInt(POSITION, position)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(POSITION)!!
        when (position) {
            0 -> {
                tvStep.text = getString(R.string.step_1)
                viewHome.setBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.fist_viewpager_color
                ))
            }
            1 -> {
                tvStep.text = getString(R.string.step_2)
                viewHome.setBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.second_viewpager_color
                ))
            }
            2 -> {
                tvStep.text = getString(R.string.step_3)
                viewHome.setBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.third_viewpager_color
                ))
            }
        }
    }
}
