package asiantech.internship.summer.viewPager_tabLayout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import asiantech.internship.summer.cuongle.debug.R

/**
 * A simple [Fragment] subclass.
 */
class AnotherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_another, container, false)
    }


}
