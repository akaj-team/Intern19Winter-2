package asiantech.internship.summer.savedata.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.Utils
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.ToDoModel
import kotlinx.android.synthetic.`at-nguyenha`.activity_edit_to_do.*

class AddEditToDoActivity : AppCompatActivity() {

    private var db: ConnectDataBase? = null
    private var getAction: String? = null
    private var toDo : ToDoModel? = null
    private var idToEdit: Int? = null
    private var idAccount: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_do)
        db = ConnectDataBase.getInMemoryDatabase(this)
        initListener()
        initAction()
    }

    private fun initAction() {
        val bundle = intent.extras
        idToEdit = bundle?.getInt(Utils.PUT_ID_ACCOUNT)
        idAccount = bundle?.getInt(Utils.PUT_ID_ACCOUNT)
        getAction = bundle?.getString(Utils.ACTION)
        if (getAction == Utils.ACTION_ADD) {
            btnUpdateToDo.text = getString(R.string.button_text_add_todo)
        } else if (getAction == Utils.ACTION_EDIT) {
            btnUpdateToDo.text = getString(R.string.button_text_edit_todo)
        }
    }

    private fun initListener() {
        btnUpdateToDo.setOnClickListener {
            when (getAction) {
                Utils.ACTION_ADD -> {
                    addToDo()
                }
                else -> {
                    editTodo()
                }
            }
            super.onBackPressed()
        }
    }

    private fun addToDo() {
        toDo = idAccount?.let {
            ToDoModel(
                toDoName = edtEditToDo.text.toString(),
                accountId = it,
                status = false)
        }
        toDo?.let { it1 -> db?.toDoDao()?.insertToDo(it1) }
        Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun editTodo() {
        toDo = db?.toDoDao()?.selectById(idToEdit!!)
        if (toDo != null) {
            toDo?.idToDo?.let {
                db?.toDoDao()?.editStatus(idToEdit!!, edtEditToDo.text.toString())
            }
        }
    }
}
