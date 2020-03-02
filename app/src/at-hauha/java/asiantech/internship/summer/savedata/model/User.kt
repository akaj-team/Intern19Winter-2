package asiantech.internship.summer.savedata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "username") var username: String,
        @ColumnInfo(name = "password") var password: String,
        @ColumnInfo(name = "path") var path: String
) : Serializable