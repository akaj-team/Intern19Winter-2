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
import kotlinx.android.synthetic.`at-nguyenha`.fragment_to_do.*

class ToDoFragment : Fragment() {

    private var listToDo = listOf<ToDoModel>()
    private var adapterListToDo = ListToDoAdapter(listToDo)
    private var db: ConnectDataBase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerToDo.layoutManager = LinearLayoutManager(requireContext())


        db = ConnectDataBase.getInMemoryDatabase(requireContext())
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

    private fun initAdapter() {

        adapterListToDo = db?.toDoDao()?.getAllToDo()?.let { ListToDoAdapter(it) }!!
        recyclerToDo.adapter = adapterListToDo
    }
}
