package asiantech.internship.summer.savedata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountModel(
        @PrimaryKey(autoGenerate = true) val accountId: Int = 0,
        @ColumnInfo(name = "userName") var userName: String?,
        @ColumnInfo(name = "nickName")var nickName: String?,
        @ColumnInfo(name = "password")var password: String?,
        @ColumnInfo(name = "avatar")var avatarAccount: String?)