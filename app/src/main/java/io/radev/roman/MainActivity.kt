package io.radev.roman

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.common.api.ApiException
import io.radev.roman.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    val CHANNEL_ID = "romanNotification"
    val CHANNEL_NAME = "romanNotificationChannelName"
    val CHANNEL_DESCRIPTION = "romanNotificationChannelDesc"
    val NOTIFICATION_ID = 100
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        binding.sendBtn.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {
        //https://faq.whatsapp.com/android/im-an-android-developer-how-can-i-integrate-whatsapp-with-my-app
        //https://stackoverflow.com/questions/19081654/send-text-to-specific-contact-programmatically-whatsapp
        //https://stackoverflow.com/questions/12952865/how-to-share-text-to-whatsapp-from-my-app
        val smsNumber = "447904619409" //without '+'
        // Create an explicit intent for an Activity in your app
        val intent = Intent("android.intent.action.MAIN").apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, binding.messageTv.text.toString())
            putExtra("jid", "$smsNumber@s.whatsapp.net")
            setPackage("com.whatsapp")
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(binding.messageTv.text.toString())
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(binding.messageTv.text.toString())
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

//    private fun getWhatsappIntent(): PendingIntent {
//        val smsNumber = "447904619409" //without '+'
//        val sendIntent = Intent("android.intent.action.MAIN")
//        //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
//        sendIntent.action = Intent.ACTION_SEND
//        sendIntent.type = "text/plain"
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
//        sendIntent.putExtra(
//            "jid",
//            "$smsNumber@s.whatsapp.net"
//        ) //phone number without "+" prefix
//        sendIntent.setPackage("com.whatsapp")
//
//        return PendingIntent.getActivity(this, 0, sendIntent, 0)
//    }

}