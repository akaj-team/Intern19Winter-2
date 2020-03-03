package asiantech.internship.summer.savedata.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.ListToDoAdapter
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.ToDoModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_done.*

class DoneFragment : Fragment() {

    private var listToDo: MutableList<ToDoModel>? = null
    private var adapterListToDo: ListToDoAdapter? = null
    private var db: ConnectDataBase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = ConnectDataBase.getInMemoryDatabase(requireContext())
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

    private fun initAdapter() {
        listToDo = db?.toDoDao()?.selectToDo(true)
        adapterListToDo = listToDo?.let { ListToDoAdapter(it) }
        recyclerDone.layoutManager = LinearLayoutManager(requireContext())
        recyclerDone.adapter = adapterListToDo
    }
}
