package asiantech.internship.summer.savedata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import asiantech.internship.summer.savedata.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun selectAll(): MutableList<Todo>

    @Query("SELECT * FROM todo WHERE todo.isStatus = :isStatus AND userId = :uid ORDER BY todo.id ASC LIMIT 12")
    fun selectAllByStatus(isStatus: Boolean, uid: Int): MutableList<Todo>

    @Query("SELECT * FROM todo WHERE todo.isStatus = :isStatus AND userId = :uid ORDER BY todo.id ASC LIMIT :index,:lastIndex")
    fun selectOffset(isStatus: Boolean, uid: Int, index: Int, lastIndex: Int): MutableList<Todo>

    @Query("SELECT * FROM todo WHERE userId = :uid")
    fun selectTodoById(uid: Int): List<Todo>

    @Query("UPDATE todo SET todo = :newTodo WHERE id = :id ")
    fun updateTodo(newTodo: String, id: Int)

    @Query("UPDATE todo SET isStatus = :isStatus WHERE id = :id ")
    fun updateStatus(isStatus: Boolean, id: Int)

    @Delete
    fun deleteTodo(todo: Todo)

    @Insert
    fun insertTodo(vararg todo: Todo)
}