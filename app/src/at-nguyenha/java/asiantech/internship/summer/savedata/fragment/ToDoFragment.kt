package asiantech.internship.summer.savedata.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.activity.AddEditToDoActivity
import asiantech.internship.summer.savedata.adapter.ListToDoAdapter
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.ToDoModel
import asiantech.internship.summer.savedata.model.Utils
import kotlinx.android.synthetic.`at-nguyenha`.fragment_to_do.*
import kotlinx.android.synthetic.`at-nguyenha`.item_todo.*

class ToDoFragment : Fragment() {

    private var listToDo : MutableList<ToDoModel>? = null
    private var adapterListToDo : ListToDoAdapter? = null
    private var db: ConnectDataBase? = null
    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = ConnectDataBase.getInMemoryDatabase(requireContext())
        initAdapter()
        initScroll()
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
                listToDo?.removeAt(it)
                adapterListToDo?.notifyDataSetChanged()
            }
        }
        adapterListToDo?.onItemDeleteClicked = {
            listToDo?.get(it)?.let { it1 -> db?.toDoDao()?.deleteToDo(it1) }
            listToDo?.removeAt(it)
            adapterListToDo?.notifyDataSetChanged()
        }
        adapterListToDo?.onItemEditClicked = {
            val idItem = listToDo?.get(it)?.idToDo!!
            val intent = Intent(activity,AddEditToDoActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(Utils.PUT_ID_TODO, idItem)
            bundle.putString(Utils.ACTION, Utils.ACTION_EDIT)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        recyclerToDo.layoutManager = LinearLayoutManager(requireContext())
        recyclerToDo.adapter = adapterListToDo
    }

    private fun initScroll() {
        recyclerToDo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == listToDo!!.size - 1) {
                        progressLoadMore.visibility = View.VISIBLE
                        Handler().postDelayed({
                            val listAddToDo = db?.toDoDao()?.selectToDoOffset(false, lastVisibleItem, 10)
                            listAddToDo?.let { listToDo?.addAll(it) }
                            adapterListToDo?.notifyDataSetChanged()
                            progressLoadMore.visibility = View.INVISIBLE
                            isLoading = true
                        }, Utils.DELAY_TIME)
                    }
                    isLoading = false
                }
            }
        })
    }
}
