package asiantech.internship.summer.savedata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoModel(
        @PrimaryKey(autoGenerate = true) val idToDo: Int = 0,
        @ColumnInfo(name = "accountid") val accountId: Int,
        @ColumnInfo(name = "todoname") var toDoName: String?,
        @ColumnInfo(name = "status") var status: Int = 0)
