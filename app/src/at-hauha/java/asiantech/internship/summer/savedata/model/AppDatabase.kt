package asiantech.internship.summer.savedata.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import asiantech.internship.summer.savedata.dao.TodoDao
import asiantech.internship.summer.savedata.dao.UserDao


@Database(entities = [User::class,Todo::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun connectDatabase(context: Context): AppDatabase? {
            return if (INSTANCE == null) {
                Room.databaseBuilder(context, AppDatabase::class.java, "my-db").allowMainThreadQueries().build()
            } else {
                INSTANCE
            }
        }
    }

    abstract fun userDao() : UserDao
    abstract fun todoDao() : TodoDao
}
