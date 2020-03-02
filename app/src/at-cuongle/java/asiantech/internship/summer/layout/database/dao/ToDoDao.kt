package asiantech.internship.summer.layout.database.dao

import androidx.room.*
import asiantech.internship.summer.layout.database.model.ToDoList

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAllTask(): MutableList<ToDoList>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun findTaskById(id: Int): ToDoList

    @Insert
    fun insertTask(vararg todoTitle: ToDoList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(vararg todoTitle: ToDoList)
//    @Query("UPDATE todo SET todoTitle =:todoTitle WHERE id =:id")
//    fun updateTask( todoTitle: String, id: Int)

    @Delete
    fun deleteTask(vararg todoTitle: ToDoList)

}
