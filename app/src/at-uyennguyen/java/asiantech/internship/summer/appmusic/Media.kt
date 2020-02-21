package asiantech.internship.summer.appmusic

import android.os.Parcel
import android.os.Parcelable

class Media(var nameSong: String, var singer: String, var time: String, var thumbnail: String, var path: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(nameSong)
        dest?.writeString(singer)
        dest?.writeString(time)
        dest?.writeString(thumbnail)
        dest?.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Media> {
        override fun createFromParcel(parcel: Parcel): Media {
            return Media(parcel)
        }

        override fun newArray(size: Int): Array<Media?> {
            return arrayOfNulls(size)
        }
    }
}
