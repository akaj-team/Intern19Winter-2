package asiantech.internship.summer.apptodolist

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseManager(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    private var SHARED_PREFERENCES_NAME = "sharepreferences"

    companion object {
        private val SHARED_ID = "id"
        private val DATABASE_NAME = "user.db"
        private val DATABASE_VER = 1
        private val TABLE_NAME = "User"
        private val COL_ID = "Id"
        private val COL_NAME = "Name"
        private val COL_NICKNAME = "Nickname"
        private val COL_PASSWORD = "Password"
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

    fun addUser(user: User) : Int{
        val db: SQLiteDatabase = this.writableDatabase
        val value = ContentValues()
        value.put(COL_NAME, user.nameUser)
        value.put(COL_NICKNAME, user.nickName)
        value.put(COL_PASSWORD, user.passWord)
        var idUser : Int = db.insert(TABLE_NAME, null, value).toInt()
        addIdSharedPreferences(idUser)
        db.close()
        return idUser
    }

    fun addIdSharedPreferences(id : Int) {
        var sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(SHARED_ID,id)
        editor.apply()
    }

    fun getid() {
//        var sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
//        var id: Int = sharedPreferences.getInt(SHARED_ID, 777)
//        Log.d("id", id.toString())
    }

    fun getAllUsers(): ArrayList<User> {
        var listUser: ArrayList<User> = ArrayList()
        var selectQuery: String = "SELECT * FROM " + TABLE_NAME
        var db: SQLiteDatabase = this.writableDatabase
        var cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                var id = cursor.getInt(0)
                var name = cursor.getString(1)
                var nickname = cursor.getString(2)
                var password: String = cursor.getString(3)
                var user: User = User(id, name, nickname, password)
                listUser.add(user)
            } while (cursor.moveToNext())
        }
        db.close()
        return listUser
    }
    fun loginUser(userName: String, password: String){
        var selectQuery : String = "SELECT * FROM $TABLE_NAME  WHERE $COL_NAME = ? AND $COL_PASSWORD = ?"
        var
    }
}