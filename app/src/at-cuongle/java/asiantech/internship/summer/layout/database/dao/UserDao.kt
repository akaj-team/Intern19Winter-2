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

    @Query("SELECT * FROM user WHERE username = :userEmail")
    fun findByEmail(userEmail: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Query("UPDATE user SET username =:userName, path =:path WHERE uid =:id ")
    fun updateData(id: Int, userName: String, path: String?)
}
