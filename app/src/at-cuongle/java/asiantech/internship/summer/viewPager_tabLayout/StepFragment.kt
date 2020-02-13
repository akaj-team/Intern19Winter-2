package asiantech.internship.summer.viewPager_tabLayout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.`at-cuongle`.activity_main.*
import kotlinx.android.synthetic.`at-cuongle`.activity_viewpager.*

class TabLayoutFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HomeAdapter(childFragmentManager)
//        viewPager.adapter = adapter
//        tabLayout.setupWithViewPager(viewPager)
    }
}
