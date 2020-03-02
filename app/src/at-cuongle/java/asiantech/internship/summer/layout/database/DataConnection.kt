package asiantech.internship.summer.layout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import asiantech.internship.summer.layout.database.dao.ToDoDao
import asiantech.internship.summer.layout.database.dao.UserDao
import asiantech.internship.summer.layout.database.model.ToDoList
import asiantech.internship.summer.layout.database.model.User

@Database(entities = [User::class, ToDoList::class], version = 1, exportSchema = false)
abstract class DataConnection : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun toDoDao(): ToDoDao

    companion object {
        private var INSTANCE: DataConnection? = null
        fun connectData(context: Context): DataConnection? {
            return if (INSTANCE == null) {
                Room.databaseBuilder(context, DataConnection::class.java, "my-db").allowMainThreadQueries().build()
            } else
                INSTANCE
        }
    }

    fun destroyDataBase() {
        INSTANCE = null
    }
}
