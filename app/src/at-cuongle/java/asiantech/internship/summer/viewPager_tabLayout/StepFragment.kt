package asiantech.internship.summer.viewPager_tabLayout


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_step.*


class StepFragment : Fragment() {

    companion object {

        private const val KEY_POSITION = "position"

        fun newInstance(position: Int): StepFragment {
            val fragment = StepFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_POSITION, position)
            }
            return fragment
        }
    }

    private var position = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(KEY_POSITION)!!
        when (position) {
            0 -> {
                rlHome.setBackgroundColor(Color.GREEN)
                tvTitle.text = getString(R.string.tv_title, position + 1)
            }
            1 -> {
                tvTitle.text = getString(R.string.tv_title, position + 1)

            }
            2 -> {
                rlHome.setBackgroundColor(Color.RED)
                tvTitle.text = getString(R.string.tv_title, position + 1)
            }
        }
    }
}
