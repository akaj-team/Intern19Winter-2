package asiantech.internship.summer.viewPagerAndTagLayout

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(POSITION)!!
        when (position) {
            0 -> {
                tvStep.text = "Step 1"
                viewHome.setBackgroundColor(Color.parseColor("#FCFFBA52"))
            }
            1 -> {
                tvStep.text = "Step 2"
                viewHome.setBackgroundColor(Color.parseColor("#FCFF8952"))
            }
            2 -> {
                tvStep.setText("Step 3")
                viewHome.setBackgroundColor(Color.parseColor("#FCFF5252"))
            }
        }
    }
}
