package asiantech.internship.summer.savedata.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.Todo
import kotlinx.android.synthetic.`at-hauha`.fragment_todo.*


@Suppress("DEPRECATION")
class DoneFragment : Fragment() {

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            initAdapter()
        }
    }

    companion object {
        private const val ARG_ID = "userId"
        private const val ARG_STATUS = "isStatus"
        fun newInstance(userId: Int, isStatus: Boolean) = DoneFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, userId)
                putBoolean(ARG_STATUS, isStatus)
            }
        }
    }

    private var adapter: TodoAdapter? = null
    private var todoList: List<Todo>? = null
    private var db: AppDatabase? = null
    private var userId = 0
    private var isStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            userId = getInt(ARG_ID)
            isStatus = getBoolean(ARG_STATUS)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.connectDatabase(requireContext())
        initAdapter()
    }

    private fun initAdapter() {
        todoList = db?.todoDao()?.selectAllByStatus(isStatus, userId)
        adapter = todoList?.let { TodoAdapter(it) }
        recycle_view.layoutManager = LinearLayoutManager(requireContext())
        recycle_view.adapter = adapter
    }

}
