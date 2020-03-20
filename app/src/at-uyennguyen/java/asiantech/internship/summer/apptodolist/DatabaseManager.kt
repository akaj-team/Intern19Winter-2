package asiantech.internship.summer.apptodolist

import android.annotation.SuppressLint
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
        private const val TABLE_USER = "User"
        private const val COL_ID_USER = "Id"
        private const val COL_NAME = "Name"
        private const val COL_NICKNAME = "Nickname"
        private const val COL_PASSWORD = "Password"
        private const val COL_AVATAR = "Avatar"

        private const val TABLE_TODO = "Todo"
        private const val COL_ID_TODO = "IdTodo"
        private const val COL_TEXT_TODO = "TextTodo"
        private const val COL_ID_USER_TODO = "IdUserTodo"

        private const val TABLE_DONE_TODO = "DoneTodo"
        private const val COL_ID_DONE_TODO = "IdDoneTodo"
        private const val COL_TEXT_DONE_TODO = "TextDoneTodo"

        private const val CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
                COL_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_NICKNAME + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_AVATAR + " TEXT)"
        private const val CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO + " (" +
                COL_ID_TODO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ID_USER_TODO + " INTEGER REFERENCES $TABLE_USER, " +
                COL_TEXT_TODO + " TEXT)"
        private const val CREATE_TABLE_DONE_TODO = "CREATE TABLE " + TABLE_DONE_TODO + " (" +
                COL_ID_DONE_TODO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ID_USER_TODO + " INTEGER REFERENCES $TABLE_USER, " +
                COL_TEXT_DONE_TODO + " TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USER)
        db?.execSQL(CREATE_TABLE_TODO)
        db?.execSQL(CREATE_TABLE_DONE_TODO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("data", "onUpgrade")
    }

    fun addDoneTodo(todo: Todo) {
        val db: SQLiteDatabase = this.writableDatabase
        val value = ContentValues()
        value.put(COL_ID_USER_TODO, todo.idUserTodo)
        value.put(COL_TEXT_DONE_TODO, todo.textTodo)
        db.insert(TABLE_DONE_TODO, null, value).toInt()
        db.close()
    }

    fun addTodo(todo: Todo) {
        val db: SQLiteDatabase = this.writableDatabase
        val value = ContentValues()
        value.put(COL_ID_USER_TODO, todo.idUserTodo)
        value.put(COL_TEXT_TODO, todo.textTodo)
        db.insert(TABLE_TODO, null, value).toInt()
        db.close()
    }

    fun addUser(user: User): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val value = ContentValues()
        value.put(COL_NAME, user.nameUser)
        value.put(COL_NICKNAME, user.nickName)
        value.put(COL_PASSWORD, user.passWord)
        value.put(COL_AVATAR, user.avatar)
        val idUser: Int = db.insert(TABLE_USER, null, value).toInt()
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

    @SuppressLint("Recycle")
    fun loginUser(userName: String, password: String): ArrayList<User> {
        val listUser: ArrayList<User> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_USER  WHERE $COL_NAME = ? AND $COL_PASSWORD = ?"
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, arrayOf(userName, password))
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val idUser = cursor.getInt(0)
                val name = cursor.getString(1)
                val nickname = cursor.getString(2)
                val pass = cursor.getString(3)
                val avatar = cursor.getString(4)
                val user = User(idUser, name, nickname, pass, avatar)
                listUser.add(user)
                cursor.moveToNext()
            }
        }
        db.close()
        return listUser
    }

    @SuppressLint("Recycle")
    fun getUserById(id: Int): ArrayList<User> {
        val listUser: ArrayList<User> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_USER  WHERE $COL_ID_USER= ?"
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val idUser = cursor.getInt(0)
                val name = cursor.getString(1)
                val nickname = cursor.getString(2)
                val password = cursor.getString(3)
                val avatar = cursor.getString(4)
                val user = User(idUser, name, nickname, password, avatar)
                listUser.add(user)

                cursor.moveToNext()
            }
        }
        db.close()
        return listUser
    }

    fun updateUser(user: User): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, user.nameUser)
        values.put(COL_NICKNAME, user.nickName)
        values.put(COL_PASSWORD, user.passWord)
        return db.update(TABLE_USER, values, "$COL_ID_USER = ?", arrayOf(user.idUser.toString()))
    }

    fun updateTodo(todo: Todo): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_TEXT_TODO, todo.textTodo)
        return db.update(TABLE_TODO, values, "$COL_ID_TODO = ?", arrayOf(todo.idTodo.toString()))
    }

    fun deleteTodo(idTodo: Int): Int {
        val db: SQLiteDatabase = this.writableDatabase
        return db.delete(TABLE_TODO, "$COL_ID_TODO = ?", arrayOf(idTodo.toString()))
    }

    @SuppressLint("Recycle")
    fun getAllTodo(id: Int): ArrayList<Todo> {
        val listTodo: ArrayList<Todo> = ArrayList()
        val selectQuery = "SELECT * FROM  $TABLE_TODO WHERE $COL_ID_USER_TODO= ?"
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            do {
                val idTodo = cursor.getInt(0)
                val idUser = cursor.getInt(1)
                val text = cursor.getString(2)
                val todo = Todo(idTodo, idUser, text)
                listTodo.add(todo)
            } while (cursor.moveToNext())
        }
        db.close()
        return listTodo
    }

    @SuppressLint("Recycle")
    fun getAllDoneTodo(id: Int): ArrayList<Todo> {
        val listTodo: ArrayList<Todo> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_DONE_TODO  WHERE $COL_ID_USER_TODO= ? "
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            do {
                val idTodo = cursor.getInt(0)
                val idUser = cursor.getInt(1)
                val text = cursor.getString(2)
                val todo = Todo(idTodo, idUser, text)
                listTodo.add(todo)
            } while (cursor.moveToNext())
        }
        db.close()
        return listTodo
    }
}
