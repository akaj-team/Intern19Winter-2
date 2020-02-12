package asiantech.internship.summer.viewPager_tabLayout


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.activity_main.*
import kotlinx.android.synthetic.`at-cuongle`.fragment_home.*
import me.relex.circleindicator.CircleIndicator


class HomeFragment : Fragment() {
    private var position = 0
    companion object {
        private const val ARG_POSITION = "position"
        fun newInstance(position: Int) = HomeFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, position)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt(ARG_POSITION, 1) ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        indicator.setViewPager(viewPager)
//        indicator.createIndicators(3, 0)
//        indicator.animatePageSelected(position)
        when (position) {
            0 -> {
                rlHome.setBackgroundColor(Color.GREEN)
                tvTitle.text = getString(R.string.tv_title, position + 1)
                tvAction.text = "Skip"


            }
            1 -> {
                tvTitle.text = getString(R.string.tv_title, position + 1)
                tvAction.text = "Skip"
                tvAction.setOnClickListener {
                    position = 2
                }
            }
            2 -> {
                rlHome.setBackgroundColor(Color.RED)
                tvTitle.text = getString(R.string.tv_title, position + 1)
                tvAction.text = "Next"
            }
        }
        tvAction.setOnClickListener {
            position += 1
            Log.i("XXX", "aaaa")
        }
        tvTitle.setOnClickListener {
            (activity as HomeActivity).replaceFragment(TabLayoutFragment())
        }
//        Log.i("XXX", "{position$position}")
    }

}
