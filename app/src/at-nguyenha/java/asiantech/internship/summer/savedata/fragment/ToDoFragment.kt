package asiantech.internship.summer.savedata.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.ListToDoAdapter
import asiantech.internship.summer.savedata.model.ToDoModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_to_do.*

class ToDoFragment : Fragment() {

    private var listToDo = mutableListOf<ToDoModel>()
    private var adapterListToDo = ListToDoAdapter(listToDo)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerToDo.layoutManager = LinearLayoutManager(requireContext())
        recyclerToDo.adapter = adapterListToDo
        initData()
    }

    private fun initData() {
        listToDo.add(ToDoModel(1, 1, "Todo 1", 1))
        listToDo.add(ToDoModel(2, 2, "Todo 2", 1))
        listToDo.add(ToDoModel(3, 3, "Todo 3", 0))
        listToDo.add(ToDoModel(4, 4, "Todo 4", 1))
        listToDo.add(ToDoModel(5, 5, "Todo 5", 1))
        listToDo.add(ToDoModel(6, 6, "Todo 6", 0))
    }
}
