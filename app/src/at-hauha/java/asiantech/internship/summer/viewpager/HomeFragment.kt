package asiantech.internship.summer.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(mPosition: Int) = HomeFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, mPosition)
            }
        }
    }

    private var mPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mPosition = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (mPosition) {
            0 -> tvStep.text = getString(R.string.textview_step, mPosition + 1)
            1 -> {
                viewMain.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                tvStep.text = getString(R.string.textview_step, mPosition + 1)
            }
            2 -> {
                viewMain.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                tvStep.text = getString(R.string.textview_step, mPosition + 1)
            }
        }

    }
}
