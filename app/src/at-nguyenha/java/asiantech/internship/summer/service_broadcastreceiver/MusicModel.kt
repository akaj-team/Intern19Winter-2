package asiantech.internship.summer.service_broadcastreceiver

import android.net.Uri

data class MusicModel(var musicName: String,
                      var musicArtist: String,
                      var musicImage: Uri,
                      var musicDuration: Long)