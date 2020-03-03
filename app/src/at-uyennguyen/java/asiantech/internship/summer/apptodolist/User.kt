package asiantech.internship.summer.apptodolist

import android.os.Parcel
import android.os.Parcelable

data class User (var idUser : Int , var nameUser : String, var nickName : String, var passWord : String, var avatar : String):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idUser)
        parcel.writeString(nameUser)
        parcel.writeString(nickName)
        parcel.writeString(passWord)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
