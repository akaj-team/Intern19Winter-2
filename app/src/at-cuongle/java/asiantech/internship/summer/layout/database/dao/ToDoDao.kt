package asiantech.internship.summer.layout.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import asiantech.internship.summer.layout.database.model.ToDoList

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo WHERE isDone = 0 AND uid =:uid ORDER BY id ASC LIMIT 10")
    fun getAllTaskStatusFalse(uid: Int): MutableList<ToDoList>

    @Query("SELECT * FROM todo WHERE isDone = 1 AND uid =:uid")
    fun getAllTaskStatusTrue(uid: Int): MutableList<ToDoList>

    @Query("SELECT * FROM todo WHERE isDone =0 AND uid =:uid ORDER BY todo.id ASC LIMIT :index,:lastIndex")
    fun selectOffset(uid: Int, index: Int, lastIndex: Int): MutableList<ToDoList>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun findTaskById(id: Int): ToDoList

    @Query("SELECT todo.id FROM todo WHERE todo.todoTitle = :title")
    fun findId(title: String): Int

    @Insert
    fun insertTask(todoTitle: ToDoList)

    @Query("UPDATE todo SET todoTitle =:title WHERE id =:id ")
    fun updateTask(id: Int, title: String)

    @Query("DELETE FROM todo WHERE todoTitle =:todoTitle")
    fun deleteTask(todoTitle: String)

    @Query("UPDATE todo SET isDone =:status WHERE id =:id")
    fun updateStatus(id: Int, status: Boolean)
}
