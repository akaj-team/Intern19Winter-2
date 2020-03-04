package asiantech.internship.summer.apptodolist

import android.os.Parcel
import android.os.Parcelable

class Todo(var idTodo: Int, var idUserTodo: Int, var textTodo: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idTodo)
        parcel.writeInt(idUserTodo)
        parcel.writeString(textTodo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Todo> {
        override fun createFromParcel(parcel: Parcel): Todo {
            return Todo(parcel)
        }

        override fun newArray(size: Int): Array<Todo?> {
            return arrayOfNulls(size)
        }
    }
}
