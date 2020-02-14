package asiantech.internship.summer.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.`at-hauha`.fragment_tablayout.*

class MenuFragment : Fragment() {

    private lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tablayout, container, false)
        tabLayout = view.findViewById(R.id.tabs)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
    }

    private fun setViewPager() {
        val adapter = MenuAdapter(childFragmentManager)
        adapter.apply {
            addTitle("HOME")
            addTitle("INFO")
            addTitle("ANOTHER")
        }
        viewPager2.adapter = adapter
        tabLayout.setupWithViewPager(viewPager2)
    }

}
