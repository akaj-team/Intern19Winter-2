package asiantech.internship.summer.layout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_done.*

class DoneFragment : Fragment() {
    companion object {
        private const val ARG_TITLE = "title"
        internal fun newInstance(title: String) = DoneFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
            }
        }
    }

    private lateinit var doneAdapter: Done
    private var doneItems = mutableListOf<ToDo>()
    private var titleDone = ""

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            arguments.let {
                titleDone = it?.getString(ARG_TITLE).toString()
                Log.i("XXX", titleDone)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        doneAdapter = Done(doneItems)
        recyclerViewDone.adapter = doneAdapter
    }

    private fun initData() {
        doneItems.apply {
            add(ToDo("Eat", true))
            add(ToDo("Eat", true))
            add(ToDo("Eat", true))
            add(ToDo("Eat", true))
            add(ToDo("Eat", true))
            add(ToDo("Eat", true))
            add(ToDo("Eat", true))
            add(ToDo("Eat", true))
        }
        doneAdapter.notifyDataSetChanged()
    }
}
