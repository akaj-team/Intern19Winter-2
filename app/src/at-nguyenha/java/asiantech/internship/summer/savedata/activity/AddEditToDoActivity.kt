package asiantech.internship.summer.savedata.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.Utils
import asiantech.internship.summer.savedata.database.ConnectDataBase
import asiantech.internship.summer.savedata.model.ToDoModel
import kotlinx.android.synthetic.`at-nguyenha`.activity_edit_to_do.*

class AddEditToDoActivity : AppCompatActivity() {

    private var db: ConnectDataBase? = null
    private var getAction : String? = ""
    private var toDo : ToDoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_do)
        db = ConnectDataBase.getInMemoryDatabase(this)

        getAction = intent.getStringExtra(Utils.ACTION)
        if (getAction == Utils.ACTION_ADD){
            btnUpdateToDo.text = getString(R.string.button_text_add_todo)
        } else if( getAction == Utils.ACTION_EDIT){
            btnUpdateToDo.text = getString(R.string.button_text_edit_todo)
        }

        btnUpdateToDo.setOnClickListener {
            toDo = ToDoModel(
                    toDoName = edtEditToDo.text.toString(),
                    accountId = 1,
                    status = 0)
            toDo?.let { it1 -> db?.toDoDao()?.insertToDo(it1) }
            Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
    }
}
