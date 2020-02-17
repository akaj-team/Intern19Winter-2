package asiantech.internship.summer.viewpager


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R

import kotlinx.android.synthetic.`at-daiho`.fragment_tutorial.*

class TutorialFragment : Fragment() {

    companion object {
        private const val KEY_POSITION = "position"
        private const val KEY_POSITION_0 = 0
        private const val KEY_POSITION_1 = 1
        private const val KEY_POSITION_2 = 2

        fun newInstance(position: Int): TutorialFragment {
            val fragment = TutorialFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_POSITION, position)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val step = arguments?.getInt(KEY_POSITION) ?: 0
        tvStep.text = String.format(view.context.getString(R.string.fragment_tutorial_step, step + 1))
        when (step) {
            KEY_POSITION_0 -> {
                container.setBackgroundColor(Color.GREEN)
            }
            KEY_POSITION_1 -> {
                container.setBackgroundColor(Color.YELLOW)
            }
            KEY_POSITION_2 -> {
                container.setBackgroundColor(Color.LTGRAY)
            }
        }
    }
}
