package asiantech.internship.summer.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_blank.*

class BlankFragment : Fragment() {

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(mPosition: Int) = BlankFragment().apply {
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
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (mPosition) {
            0 -> flContainer.setBackgroundResource(R.drawable.ic_damson_crumble)
            1 -> flContainer.setBackgroundResource(R.drawable.ic_noodle)
            2 -> flContainer.setBackgroundResource(R.drawable.ic_white_chocolate_cheesecake)
        }
    }

}
