package asiantech.internship.summer.savedata.dao

import androidx.room.Dao
import androidx.room.Insert
import asiantech.internship.summer.savedata.model.Todo

@Dao
interface TodoDao {
    @Insert
    fun inserTodo(todo: Todo)
}