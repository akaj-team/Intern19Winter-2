package asiantech.internship.summer.layout


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_main_screen.*

class MainScreenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapter = ToDoAdapter(childFragmentManager)
        adapter.apply {
            addFragment(ToDoFragment(), "TODO")
            addFragment(DoneFragment(), "DONE")
        }
        viewPagerTodo.adapter = adapter
        tabLayoutTodo.setupWithViewPager(viewPagerTodo)
        tabLayoutTodo.setBackgroundColor(Color.GREEN)
    }
}
