package com.example.mad_pra_8_106_5b6

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.example.mad_pra_8_106_5b6.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cardAlarmcreate2.visibility = View.GONE
        binding.btncreatealarm.setOnClickListener() {
            showTimerDialog()
        }
        binding.btnCancleAlaram.setOnClickListener{
            setAlarm(-1,"Stop")
            binding.cardAlarmcreate1.visibility = View.GONE
        }
    }

    private fun showTimerDialog() {
        val cldr: Calendar = Calendar.getInstance()
        val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cldr.get(Calendar.MINUTE)
        val picker = TimePickerDialog(
            this, { tp, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute) },
            hour,
            minutes,
            false
        )
        picker.show()
    }

    @SuppressLint("SetTextI18n")
    private fun sendDialogDataToActivity(hour: Int, minute: Int) {
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hour, minute, 0)
        binding.textAlarmTime.text =  SimpleDateFormat("hh:mm:ss a ", Locale.getDefault()).format(alarmCalendar.time) + "$month,$day $year"
        binding.cardAlarmcreate2.visibility = View.VISIBLE
        setAlarm(alarmCalendar.timeInMillis, "Start")
    }

    private fun setAlarm(millisTime: Long, str: String){
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1",str)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext,234324243, intent,PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(str == "Start"){
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                millisTime,
                pendingIntent
            )
            Toast.makeText(this,"Start Alarm",Toast.LENGTH_SHORT).show()
        }
        else if(str == "Stop"){
            alarmManager.cancel(pendingIntent)
            sendBroadcast(intent)
            Toast.makeText(this, "Stop Alarm", Toast.LENGTH_SHORT).show()
        }
    }
}