package com.example.coffeecompanion.ui.notifications

import android.media.Image
import android.os.Bundle
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
import androidx.databinding.DataBindingUtil
import com.example.coffeecompanion.databinding.FragmentNotificationsBinding
import org.w3c.dom.Text


//public lateinit var root: View;

lateinit var binding: FragmentNotificationsBinding

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

        val linlay = binding.linlay;
        mDetector = GestureDetectorCompat(getActivity(), MyGestureListener())

        linlay.setOnTouchListener(touchListener)
        return binding.root
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