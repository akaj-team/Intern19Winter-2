package asiantech.internship.summer.layout.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import asiantech.internship.summer.layout.database.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun findAll(): List<User>

    @Query("SELECT * FROM user WHERE username = :userName AND password = :passWord")
    fun findByName(userName: String, passWord: String): User

    @Insert
    fun insertAll(vararg users: User)
}
