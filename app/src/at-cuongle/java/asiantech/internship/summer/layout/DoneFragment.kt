package asiantech.internship.summer.layout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.DataConnection
import asiantech.internship.summer.layout.database.model.ToDoList
import kotlinx.android.synthetic.`at-cuongle`.fragment_done.*

class DoneFragment : Fragment() {
    private var doneAdapter: Done? = null
    private var doneList: MutableList<ToDoList>? = null
    private var db: DataConnection? = null
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            initAdapter()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataConnection.connectData(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        doneList = db?.toDoDao()?.getAllTaskStatusTrue()
        doneAdapter = doneList?.let { Done(it) }
        recyclerViewDone.adapter = doneAdapter
    }
}
