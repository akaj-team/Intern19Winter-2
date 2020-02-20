package asiantech.internship.summer.model

import android.net.Uri

data class Song(
        var title: String,
        var artist: String,
        var duration: Int,
        var cover: Uri,
        var path: Uri
)
