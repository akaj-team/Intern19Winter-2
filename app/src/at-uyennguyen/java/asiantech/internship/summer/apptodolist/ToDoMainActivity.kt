package asiantech.internship.summer.apptodolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class ToDoMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        val loginToDoFragment = LoginToDoFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayoutTodo, loginToDoFragment)
                .commit()
    }
}
