package asiantech.internship.summer.savedata.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.ListToDoAdapter
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.ToDoModel
import asiantech.internship.summer.savedata.model.Utils
import kotlinx.android.synthetic.`at-nguyenha`.fragment_done.*

@Suppress("DEPRECATION")
class DoneFragment : Fragment() {

    private var listToDo: MutableList<ToDoModel>? = null
    private var adapterListToDo: ListToDoAdapter? = null
    private var db: ConnectDataBase? = null
    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = ConnectDataBase.getInMemoryDatabase(requireContext())
        initScroll()
        initAdapter()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            val listAddToDo = db?.toDoDao()?.selectToDo(true)
            listToDo?.clear()
            listAddToDo?.let { listToDo?.addAll(it) }
            adapterListToDo?.notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        listToDo = db?.toDoDao()?.selectToDo(true)
        adapterListToDo = listToDo?.let { ListToDoAdapter(it) }
        recyclerDone.layoutManager = LinearLayoutManager(requireContext())
        recyclerDone.adapter = adapterListToDo
    }

    private fun initScroll() {
        recyclerDone.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == listToDo!!.size - 1) {
                        progressLoadDone.visibility = View.VISIBLE
                        Handler().postDelayed({
                            val listAddToDo = db?.toDoDao()?.selectToDoOffset(true, lastVisibleItem, 10)
                            listToDo?.clear()
                            listAddToDo?.let { listToDo?.addAll(it) }
                            adapterListToDo?.notifyDataSetChanged()
                            progressLoadDone.visibility = View.INVISIBLE
                            isLoading = true
                        }, Utils.DELAY_TIME)
                    }
                    isLoading = false
                }
            }
        })
    }
}
