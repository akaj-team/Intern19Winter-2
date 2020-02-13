package asiantech.internship.summer.viewPager_tabLayout


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R

class InformationFragment : Fragment() {
    companion object {
        private const val KEY_POSITION = "position"
        fun newInstance(position: Int) = InformationFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_POSITION, position)
            }
        }
    }

    private var position = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        position = arguments?.getInt(KEY_POSITION)!!
        Log.i("XXX", "infor$position")
    }

}
