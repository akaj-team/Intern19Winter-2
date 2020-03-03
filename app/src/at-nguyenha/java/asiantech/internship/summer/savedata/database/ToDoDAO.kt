package asiantech.internship.summer.savedata.database

import androidx.room.*
import asiantech.internship.summer.savedata.model.ToDoModel

@Dao
interface ToDoDAO {
    @Insert
    fun insertToDo(vararg account: ToDoModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(vararg todoTitle: ToDoModel)

    @Delete
    fun deleteToDo(todo: ToDoModel)

    @Query("select * from todo_table")
    fun getAllToDo() : MutableList<ToDoModel>

    @Query("select * from todo_table where idToDo = :id")
    fun selectById(id : Int) : ToDoModel

    @Query("select * from todo_table where status = :status")
    fun selectToDo(status : Boolean) : MutableList<ToDoModel>

    @Query("update todo_table set status = :isStatus where idToDo = :id")
    fun updateStatus(id: Int, isStatus :Boolean)

    @Query("update todo_table set todoname = :toDoName where idToDo = :id")
    fun editStatus(id: Int, toDoName : String)

}