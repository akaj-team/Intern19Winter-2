package asiantech.internship.summer.layoutandroid

import android.graphics.Bitmap

interface ChangeAvatar {
    fun imageUser(img: Bitmap?)
    fun profile(fullname : String,bio: String)
}