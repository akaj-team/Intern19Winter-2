package asiantech.internship.summer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import asiantech.internship.summer.service.Utils.NEXT_ACTION
import asiantech.internship.summer.service.Utils.PLAY_ACTION
import asiantech.internship.summer.service.Utils.PREV_ACTION

class NotificationBroadCast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            PREV_ACTION -> Toast.makeText(context, PREV_ACTION, Toast.LENGTH_SHORT).show()
            PLAY_ACTION -> Toast.makeText(context, PLAY_ACTION, Toast.LENGTH_SHORT).show()
            NEXT_ACTION -> Toast.makeText(context, NEXT_ACTION, Toast.LENGTH_SHORT).show()
        }
    }

}