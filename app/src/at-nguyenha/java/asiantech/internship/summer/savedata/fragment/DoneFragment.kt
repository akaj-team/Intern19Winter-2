package asiantech.internship.summer.savedata.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.adapter.ListToDoAdapter
import asiantech.internship.summer.savedata.model.ToDoModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_done.*

class DoneFragment : Fragment() {

    private var listToDo = listOf<ToDoModel>()
    private var adapterListToDo = ListToDoAdapter(listToDo)
    //private var db : ConnectDataBase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter(){
        //adapterListToDo = db?.toDoDao()?.selectDone()?.let { ListToDoAdapter(it) }!!
        recyclerDone.adapter = adapterListToDo

    }
}
