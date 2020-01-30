package asiantech.internship.summer.savedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPager()

    }

    private fun setPager() {
        val adapter = MenuAdapter(childFragmentManager)
        adapter.apply {
            addFragment(TodoFragment(), "todo")
            addFragment(DoneFragment(), "done")
        }
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}