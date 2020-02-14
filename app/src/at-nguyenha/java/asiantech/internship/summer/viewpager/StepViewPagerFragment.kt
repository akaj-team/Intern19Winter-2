package asiantech.internship.summer.viewpager


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.fragment_step.*

class StepViewPagerFragment : Fragment() {

    companion object {
        private const val KEY_POSITION = "position"
        fun newInstance(position: Int): StepViewPagerFragment {
            val fragment = StepViewPagerFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_POSITION, position)
            }
            return fragment
        }
    }

    private var position = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(KEY_POSITION) ?: 0
        tvStep.text = getString(R.string.textview_text_step, position + 1)
    }
}
