package com.example.coffeecompanion.ui.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.coffeecompanion.Database.CoffeeTypesDatabase
import com.example.coffeecompanion.Database.CoffeeTypesDatabaseDao
import com.example.coffeecompanion.databinding.FragmentNotificationsBinding
import kotlinx.android.synthetic.main.popup_layout.*
import org.w3c.dom.Text


//public lateinit var root: View;

lateinit var binding: FragmentNotificationsBinding
private var index: Int = 0;
private lateinit var coffeeViewmodel: NotificationsViewModel
private lateinit var timer: CountDownTimer
private var time: Long = 10000;


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var database: CoffeeTypesDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)

        //Get objects
        val linlay = binding.linlay
        val start = binding.startbtn
        val time = binding.timeText
        val stop = binding.pausebtn

        //Database stuff
        val application = requireNotNull(this.activity).application
        database = CoffeeTypesDatabase.getInstance(application).coffeeTypesDatabaseDao
        notificationsViewModel.coffee = database.getAllCoffee()
        coffeeViewmodel = notificationsViewModel

        //Gesture things
        mDetector = GestureDetectorCompat(getActivity(), MyGestureListener())
        linlay.setOnTouchListener(touchListener)

        start.setOnClickListener { startTimer() }
        stop.setOnClickListener { pauseTimer() }

        return binding.root
    }

    fun startTimer() {
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                val remainingSecs = p0 / 1000
                val min = remainingSecs / 60
                val seconds = remainingSecs % 60
                binding.timeText.setText("" + min + ":" + (if (seconds < 10) "0" + seconds else seconds) + "")
            }
            override fun onFinish() {
                sendNotification()
            }
        }
        timer.start()
        binding.pausebtn.visibility = View.VISIBLE
    }

    fun pauseTimer() {
        timer.cancel()
    }


    fun onSwipeRight() {
        var image = binding.imageView
        image.setImageResource(R.drawable.pourover)
    }

    var touchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            if(mDetector.onTouchEvent(event)){
                Log.i("fling", "left")
            }
            else{
                Log.i("fling", "right")
            }
            return mDetector.onTouchEvent(event)
        }
    }


    public fun sendNotification() {
        var channel_ID = "brew_alarm"
        var nm: NotificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channel_ID,
                "brew_alarm_channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Channel for the brew timer alarm"
            nm.createNotificationChannel(channel)
        }

        var intent = Intent(context, NotificationsFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        var pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        var b = NotificationCompat.Builder(context!!, "notification_channel_id")

        b = b.setAutoCancel(true).setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.frenchpress)
            .setTicker("HELLO")
            .setContentTitle("Coffee Companion")
            .setContentText("Brew is finished")
            .setContentInfo("INFO")
            .setChannelId(channel_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        nm.notify(0, b.build())
    }


    class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
        var image = binding.imageView
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
                        if (diffX > 0 && index != 0) {
                            index--;
                            
                        } else {
                            index++;

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

        fun getImage(brew: String) {
            //Get the image for the brew method
            return image.setImageResource(R.drawable.frenchpress)
        }

        fun makeTimer(ms: Int) {
            timer = object : CountDownTimer(10000, 1000) {
                override fun onTick(p0: Long) {
                    val remainingSecs = p0 / 1000
                    val min = remainingSecs / 60
                    val seconds = remainingSecs % 60
                    binding.timeText.setText("" + min + ":" + (if (seconds < 10) "0" + seconds else seconds) + "")
                }

                override fun onFinish() {
//                    sendNotification()
                }
            }
        }
    }
}

//
//
//                            index++;
//                            //Get the proper image
//                            val name = coffeeViewmodel.coffee.value!![index].name
//                            getImage(name)
//
//                            //Set time
//                            var time = coffeeViewmodel.coffee.value!![index].minsToBrew
//                            time *= 60
//                            val min = time / 60
//                            val seconds = time % 60
//                            binding.timeText.setText("" + min + ":" + (if (seconds < 10) "0" + seconds else seconds) + "")
//
//                            //Set the timer
//                            makeTimer((time * 1000) as Int);
//                            //Set title
//                            binding.brewTitle.setText(name)
////                            Log.i("fling", "right")