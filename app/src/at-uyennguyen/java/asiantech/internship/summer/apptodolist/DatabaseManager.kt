package asiantech.internship.summer.apptodolist

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseManager(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "sharepreferences"
        private const val SHARED_ID = "id"
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VER = 1
        private const val TABLE_NAME = "User"
        private const val COL_ID = "Id"
        private const val COL_NAME = "Name"
        private const val COL_NICKNAME = "Nickname"
        private const val COL_PASSWORD = "Password"
        private val CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_NICKNAME + " TEXT, " +
                COL_PASSWORD + " TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("data", "onUpgrade")
    }

    fun addUser(user: User): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val value = ContentValues()
        value.put(COL_NAME, user.nameUser)
        value.put(COL_NICKNAME, user.nickName)
        value.put(COL_PASSWORD, user.passWord)
        val idUser: Int = db.insert(TABLE_NAME, null, value).toInt()
        addIdSharedPreferences(idUser)
        db.close()
        return idUser
    }

    fun addIdSharedPreferences(id: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(SHARED_ID, id)
        editor.apply()
    }

    fun getAllUsers(): ArrayList<User> {
        val listUser: ArrayList<User> = ArrayList()
        val selectQuery: String = "SELECT * FROM " + TABLE_NAME
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val nickname = cursor.getString(2)
                val password: String = cursor.getString(3)
                val user: User = User(id, name, nickname, password)
                listUser.add(user)
            } while (cursor.moveToNext())
        }
        db.close()
        return listUser
    }

    fun loginUser(userName: String, password: String): ArrayList<User> {
        val listUser: ArrayList<User> = ArrayList()
        val selectQuery: String = "SELECT * FROM $TABLE_NAME  WHERE $COL_NAME = ? AND $COL_PASSWORD = ?"
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, arrayOf(userName, password))
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val nickname = cursor.getString(2)
                val password = cursor.getString(3)
                val user = User(id, name, nickname, password)
                listUser.add(user)
                cursor.moveToNext()
            }
        }
        db.close()
        return listUser
    }
}
