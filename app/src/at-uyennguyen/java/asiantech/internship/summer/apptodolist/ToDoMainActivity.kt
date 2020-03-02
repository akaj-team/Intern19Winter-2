package asiantech.internship.summer.apptodolist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class ToDoMainActivity : AppCompatActivity(){
    private var SHARED_PREFERENCES_NAME = "sharepreferences"
    private var USER_ID = "user_id"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        var sharedPreferences : SharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        var databaseManager : DatabaseManager = DatabaseManager(this)
        val registerToDoFragment : RegisterToDoFragment = RegisterToDoFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayoutTodo,registerToDoFragment)
                .commit()

    }
}