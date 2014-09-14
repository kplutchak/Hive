package com.hive.animation;

import com.hive.main.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;



public class SwipeDetector extends SimpleOnGestureListener {
	
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    private Context context;
	
    public SwipeDetector(Context c) {
    	this.context = c;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(this.context, "Left Swipe with x velocity of " + Float.toString(velocityX), Toast.LENGTH_SHORT).show();
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(this.context, "Right Swipe with x velocity of " + Float.toString(velocityX), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // nothing
        }
        return false;
    }

        @Override
    public boolean onDown(MotionEvent e) {
          return true;
    }
}
