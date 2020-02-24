package asiantech.internship.summer.service_broadcastreceiver.model

import android.os.Parcel
import android.os.Parcelable

data class MusicModel(var path : String,
                      var musicName: String,
                      var musicArtist: String,
                      var musicImage: String,
                      var musicDuration: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        parcel.writeString(musicName)
        parcel.writeString(musicArtist)
        parcel.writeString(musicImage)
        parcel.writeInt(musicDuration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicModel> {
        override fun createFromParcel(parcel: Parcel): MusicModel {
            return MusicModel(parcel)
        }

        override fun newArray(size: Int): Array<MusicModel?> {
            return arrayOfNulls(size)
        }
    }
}
