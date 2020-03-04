package asiantech.internship.summer.layout.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoList(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                    @ColumnInfo(name = "todoTitle") var todoTitle: String?,
                    @ColumnInfo(name = "isDone") var isDone: Boolean,
                    @ColumnInfo(name = "uid") var uid: Int
)
