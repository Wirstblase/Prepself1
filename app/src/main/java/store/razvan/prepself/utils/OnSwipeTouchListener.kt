package store.razvan.prepself.utils

import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.function.Consumer


class OnSwipeTouchListener(var context: AppCompatActivity?) : OnTouchListener {
    private val gestureDetector: GestureDetector

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    lateinit var swipeRight: Runnable
    lateinit var swipeLeft: Runnable

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > Companion.SWIPE_THRESHOLD && Math.abs(velocityX) > Companion.SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show()
                            swipeRight.run()
                        } else {
                            Toast.makeText(context, "Left", Toast.LENGTH_SHORT).show()
                            swipeLeft.run()
                        }
                        result = true
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }
    }

    fun onSwipeRight(consumer: Runnable) {
        this.swipeRight = consumer
    }
    fun onSwipeLeft(consumer: Runnable) {
        this.swipeLeft = consumer
    }
}