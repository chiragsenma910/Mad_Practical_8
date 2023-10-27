package com.example.mad_pra_8_106_5b6

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AlarmService : Service() {

    var mp : MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null){
            mp = MediaPlayer.create(this,R.raw.alarm)
            mp?.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        mp?.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}
