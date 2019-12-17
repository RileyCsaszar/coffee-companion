package com.example.coffeecompanion.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.coffeecompanion.MainActivity
import com.example.coffeecompanion.R
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.coffeecompanion.databinding.FragmentNotificationsBinding
import org.w3c.dom.Text


//public lateinit var root: View;

lateinit var binding: FragmentNotificationsBinding
private var index: Int = 0;

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var mDetector: GestureDetectorCompat


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        //root = inflater.inflate(com.example.coffeecompanion.R.layout.fragment_notifications, container, false)

        val linlay = binding.linlay
        val start = binding.startbtn
        val time = binding.timeText
        val stop = binding.pausebtn

        mDetector = GestureDetectorCompat(getActivity(), MyGestureListener())

        linlay.setOnTouchListener(touchListener)

        createNotificationChannel()

        var builder = NotificationCompat.Builder(this.context)
            .setSmallIcon(R.drawable.frenchpress)
            .setContentTitle("Coffee timer complete!")
            .setContentText("Your timer has finished, and your coffee should be ready!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val timer = object: CountDownTimer(50000, 1000) {
            override fun onTick(p0: Long) {
                val remainingSecs = p0/1000
                val min = remainingSecs / 60;
                val seconds = remainingSecs % 60;
                time.setText(""+min + ":" + (if (seconds < 10) "0" + seconds else seconds)+"")

            }
            override fun onFinish() {
                with(NotificationManagerCompat.from(context!!)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(12345, builder.build())
                }
            }
        }



        start.setOnClickListener{startTimer(timer)}
        stop.setOnClickListener{pauseTimer(timer)}


        return binding.root
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel"
            val descriptionText = "A channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE as Context) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    fun startTimer(timer :CountDownTimer){
        timer.start()
        binding.pausebtn.visibility = View.VISIBLE
    }
    fun pauseTimer(timer: CountDownTimer){
        timer.cancel()
    }


    fun onSwipeRight(){
        var image = binding.imageView
        image.setImageResource(R.drawable.pourover)
    }

    var touchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return mDetector.onTouchEvent(event)
        }
    }


    fun displayInfo(){

    }

    private class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
//                            index--;
                            var image = binding.imageView
                            image.setImageResource(R.drawable.pourover)
                            binding.timeText.setText("3:30")
                            binding.brewTitle.setText("Pourover")
                            Log.i("fling", "right")
                        } else {
                            var image = binding.imageView
                            image.setImageResource(R.drawable.frenchpress)
                            binding.timeText.setText("5:00")
                            binding.brewTitle.setText("French Press")
                            Log.i("fling", "left")
                        }
                    }
                } else {
                    // onTouch(e);
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }


    }


}