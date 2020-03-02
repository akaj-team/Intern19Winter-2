package asiantech.internship.summer.savedata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "todo") var todo: String?,
        @ColumnInfo(name = "isStatus") var isStatus: Boolean,
        @ColumnInfo(name = "userId") var userId: Int
)