package asiantech.internship.summer.service_broadcastreceiver.model

import java.util.concurrent.TimeUnit

object Units {
    fun convertTimeMusic(millis: Long): String {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }
}
