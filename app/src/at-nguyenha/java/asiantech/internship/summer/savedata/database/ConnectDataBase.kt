package asiantech.internship.summer.savedata.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import asiantech.internship.summer.savedata.model.AccountModel
import asiantech.internship.summer.savedata.model.ToDoModel

@Database(
        entities = [AccountModel::class,ToDoModel::class],
        version = 1,
        exportSchema = false
)

abstract class ConnectDataBase : RoomDatabase() {
    companion object {
        private var INSTANCE: ConnectDataBase? = null
        fun getInMemoryDatabase(context: Context): ConnectDataBase? {
            return if (INSTANCE == null)
                Room.databaseBuilder(context, ConnectDataBase::class.java, "myDB")
                        .allowMainThreadQueries()
                        .build()
            else INSTANCE
        }
    }

    abstract fun accountDao(): AccountDAO

    abstract fun toDoDao(): ToDoDAO
}
