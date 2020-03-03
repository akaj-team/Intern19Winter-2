package asiantech.internship.summer.savedata.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.Utils
import asiantech.internship.summer.savedata.activity.AddEditToDoActivity
import asiantech.internship.summer.savedata.adapter.ListToDoAdapter
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.ToDoModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_to_do.*
import kotlinx.android.synthetic.`at-nguyenha`.item_todo.*

class ToDoFragment : Fragment() {

    private var listToDo : MutableList<ToDoModel>? = null
    private var adapterListToDo : ListToDoAdapter? = null
    private var db: ConnectDataBase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = ConnectDataBase.getInMemoryDatabase(requireContext())
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

    private fun initAdapter() {
        listToDo = db?.toDoDao()?.selectToDo(false)
        adapterListToDo = listToDo?.let { ListToDoAdapter(it) }
        adapterListToDo?.onItemCheckBoxClicked = {
            if (cbStatus.isChecked){
                listToDo?.get(it)?.idToDo?.let { it1 -> db?.toDoDao()?.updateStatus(it1 ,true)}
                listToDo?.get(it)?.status = true
                adapterListToDo?.notifyDataSetChanged()
                initAdapter()
            }
        }
        adapterListToDo?.onItemDeleteClicked = {
            listToDo?.get(it)?.let { it1 -> db?.toDoDao()?.deleteToDo(it1) }
            listToDo?.removeAt(it)
            adapterListToDo?.notifyDataSetChanged()
            initAdapter()
        }
        adapterListToDo?.onItemEditClicked = {
            val idItem = listToDo?.get(it)?.idToDo!!
            val intent = Intent(activity,AddEditToDoActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(Utils.PUT_ID, idItem)
            bundle.putString(Utils.ACTION, Utils.ACTION_EDIT)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        recyclerToDo.layoutManager = LinearLayoutManager(requireContext())
        recyclerToDo.adapter = adapterListToDo

    }
}
