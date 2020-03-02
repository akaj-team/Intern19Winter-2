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
    fun deleteTask(vararg todoTitle: ToDoModel)

    @Query("select * from todo_table")
    fun getAllToDo() : List<ToDoModel>

    @Query("select * from todo_table where idToDo = :id")
    fun selectById(id : Int) : ToDoModel

    @Query("select * from todo_table where status <> 0")
    fun selectDone() : List<ToDoModel>

}