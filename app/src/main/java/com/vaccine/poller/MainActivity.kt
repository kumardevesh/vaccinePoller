package com.vaccine.poller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vaccine.poller.models.Center
import com.vaccine.poller.models.CentersResponse
import com.vaccine.poller.models.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object{
         var areaCode = 265
    }
    private var apiInterface: APIInterface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiInterface = ApiClient.getClient(this)?.create(APIInterface::class.java)
        object : CountDownTimer(100 * 30000, 30000) {
            override fun onTick(millisUntilFinished: Long) {
                makeCall()
            }

            override fun onFinish() {
                start()
            }
        }.start()
        createNotificationChannel()
    }

    fun makeCall() {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        var dt = Date()
        var c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt = c.time
        var dateString = formatter.format(dt)
        initiateCall(dateString)
    }

    private fun initiateCall(dateString: String) {
        val call: Call<CentersResponse>? = apiInterface?.getCenters(
                areaCode, dateString,
                "COVISHIELD"
        )
        call?.enqueue(object : Callback<CentersResponse?> {
            override fun onFailure(call: Call<CentersResponse?>?, t: Throwable?) {

            }

            override fun onResponse(
                    call: Call<CentersResponse?>?,
                    response: Response<CentersResponse?>?
            ) {
                val centers = response?.body()?.centers
                centers?.let {
                    for (center: Center in centers) {
                        for (session: Session in center.sessions) {
                            if (session.capacity > 0) {
                                showNotification()
                            }
                        }
                    }
                }
            }

        })
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "COVISHIELD"
            val descriptionText = "COVISHIELD"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("COVISHIELD", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification() {
        var builder = NotificationCompat.Builder(this, "COVISHIELD")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Covishield Found")
                .setContentText("Covishield Found")
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText("Covishield Found")
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)


        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(Random.nextInt(), builder.build())
        }
    }

}
