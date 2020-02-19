package asiantech.internship.summer.model

import android.net.Uri

data class Song(
        var title: String,
        var artist: String,
        var duration: Long,
        var cover: Uri
)
