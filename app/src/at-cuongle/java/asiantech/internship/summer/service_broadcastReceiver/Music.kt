package asiantech.internship.summer.service_broadcastReceiver

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore


data class Music(var duration: Int, var name: String, var uri: Uri, var artist: String, var image: Uri)
