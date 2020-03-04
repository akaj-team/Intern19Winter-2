package asiantech.internship.summer.savedata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import asiantech.internship.summer.savedata.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE username = :name")
    fun getUserName(name: String): User

    @Query("SELECT * FROM user WHERE username = :user AND password = :password")
    fun findUser(user: String, password: String): User

    @Query("SELECT * FROM user WHERE id = :id")
    fun findUserById(id: Int): User

    @Query("UPDATE user SET username = :name, password = :password, path = :path WHERE user.id = :id")
    fun updateData(name: String, password: String, path: String, id: Int)

    @Insert
    fun insertAll(vararg users: User)
}
