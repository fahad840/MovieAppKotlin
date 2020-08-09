package company.com.movieappkotlin.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import company.com.movieappkotlin.R

class NotificationWorker(context: Context,workerParameters: WorkerParameters) : Worker(context,workerParameters) {

    override fun doWork(): Result {
        createNotificationChannel()
        return Result.success()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel"
            val descriptionText = "desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("2", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val notifybuilder= NotificationCompat.Builder(applicationContext, "2")
                .setContentTitle("Movie App")
                .setContentText("This Movie has been saved to list!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
            notificationManager.notify(1,notifybuilder.build())

        }
    }
}