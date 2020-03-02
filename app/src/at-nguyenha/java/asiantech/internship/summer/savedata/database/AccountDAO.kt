package asiantech.internship.summer.savedata.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import asiantech.internship.summer.savedata.model.AccountModel

@Dao
interface AccountDAO {
    @Insert
    fun insertAccount(vararg account: AccountModel)

    @Query("select * from account where userName = :user and password = :pass")
    fun checkLogin(user: String, pass : String) : AccountModel

    @Query("select * from account")
    fun getAllAccount() : List<AccountModel>
}