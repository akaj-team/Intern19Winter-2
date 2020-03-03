package asiantech.internship.summer.savedata.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import asiantech.internship.summer.savedata.model.AccountModel

@Dao
interface AccountDAO {
    @Insert
    fun insertAccount(vararg account: AccountModel)

    @Query("select * from account where userName = :user and password = :pass")
    fun checkLogin(user: String, pass : String) : AccountModel

    @Query("select * from account")
    fun getAllAccount() : List<AccountModel>

    @Query("select * from account where accountId = :id")
    fun getAccountById(id: Int): AccountModel

    @Query("select * from account where userName = :user")
    fun checkAccountExist(user: String) : AccountModel

    @Query("update account set userName = :user, nickName = :nickname, password = :pass, avatar = :avatar where account.accountId = :id")
    fun editAccount(id: Int, user: String, nickname: String, pass: String, avatar: String)

}
