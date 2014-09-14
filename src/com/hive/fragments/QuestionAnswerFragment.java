package com.hive.fragments;

import com.hive.R;
import com.hive.animation.SwipeDetector;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionAnswerFragment extends Fragment implements OnGestureListener, OnTouchListener {

	private Handler handler = new Handler();
	
	 private GestureDetector gestureDetector;
	 private View.OnTouchListener gestureListener;
	 
	 VelocityTracker velocity;
	 
	 // TEST: last velocity
	 private float last_velocity;
	 // coordinate variables
	 private float centerX;
	 private float adjust;
	 private float centerY;
	 private LayoutParams center_lp;
	 
	 private boolean has_been_moved = false;
	 private int _xDelta;
	 private int _yDelta;
	 private MyImageView bee;
	 private ViewGroup root_layout;
	// TextViews
	
	private TextView question;
	private TextView a1;
	private TextView a2;
	
	public QuestionAnswerFragment() {
		super();
	
		// TODO Auto-generated constructor stub
	}
	

	public class MyImageView extends ImageView {
		

		public MyImageView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onAnimationEnd() {
		    super.onAnimationEnd();
		   	RelativeLayout.LayoutParams lfinParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
            lfinParams.leftMargin = 0;
            lfinParams.topMargin = 0;
            lfinParams.bottomMargin = -250;
            lfinParams.rightMargin = -250;
            this.setLayoutParams(lfinParams);
        
            this.clearAnimation();
		    
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
    	View v = inflater.inflate(R.layout.questionanswer_fragment, container, false);
    	
    	
 	   // Gesture detection
        gestureDetector = new GestureDetector(getActivity(), new SwipeDetector(getActivity()));
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
    	root_layout = (ViewGroup) v.findViewById(R.id.root_qa);
		bee = new MyImageView(getActivity());
		bee.setImageDrawable(getResources().getDrawable(R.drawable.circle_chooser));

		DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        int px = (int) Math.ceil(30 * logicalDensity);
		
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(px, px);
        
        layoutParams.leftMargin = 0;
        layoutParams.topMargin = 0;
        layoutParams.bottomMargin = -250;
        layoutParams.rightMargin = -250;
        
        this.center_lp = layoutParams;

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int width = size.x;
        
        centerY=height/2;
        centerX=width/2;

        adjust = (int) Math.ceil(15 * logicalDensity);
        
		bee.setLayoutParams(layoutParams);
		bee.setOnTouchListener(this);
		
		bee.setY(centerY - adjust);
		bee.setX(centerX - adjust);
		
		root_layout.addView(bee);

    	final View fv = v;
 
    	question = (TextView) fv.findViewById(R.id.question_text);
    	
    	question.setOnTouchListener(new View.OnTouchListener() { 
            @Override
           public boolean onTouch(View v, MotionEvent event){
                return gestureDetector.onTouchEvent(event);
           }
    	});
    	
		a1 = (TextView) fv.findViewById(R.id.answer_one);
		a2 = (TextView) fv.findViewById(R.id.answer_two);
		

        Runnable runnable = new Runnable() {
     	   @Override
     	   public void run() {

				final Animation animationFadeIn = AnimationUtils.loadAnimation(fv.getContext(),
				         android.R.anim.fade_in);

					question.startAnimation(animationFadeIn);
					a1.startAnimation(animationFadeIn);
					a2.startAnimation(animationFadeIn);
					
					question.setVisibility(View.VISIBLE);
					a1.setVisibility(View.VISIBLE);
					a2.setVisibility(View.VISIBLE);
   
     	   }
     	};

     handler.postDelayed(runnable, 500);
    	
    	return v;
    }
    
	@Override
	public boolean onDown(MotionEvent e) {
	    // TODO Auto-generated method stub
	    return true;
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	        float velocityY) {
	    // TODO Auto-generated method stub
	    Log.d("Fling", "On Fling");
	    return true;
	}
	
	@Override
	public void onLongPress(MotionEvent e) {
	    // TODO Auto-generated method stub
	
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	        float distanceY) {
	    // TODO Auto-generated method stub
	    return false;
	}
	
	@Override
	public void onShowPress(MotionEvent e) {
	    // TODO Auto-generated method stub
	
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
	    // TODO Auto-generated method stub
	    return false;
	}

	@Override
	public boolean onTouch(final View view, MotionEvent event) {
		this.has_been_moved = true;
		
		//final RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		velocity = VelocityTracker.obtain();
		
		
	     Display display = getActivity().getWindowManager().getDefaultDisplay();
	        final Point size = new Point();
	        display.getSize(size);
	        int height = size.y;
	        int width = size.x;
		
		int X = (int) event.getRawX();
		int Y = (int) event.getRawY();
		
	
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			velocity.addMovement(event);
			
			_xDelta = X - lParams.leftMargin;
			_yDelta = Y - lParams.topMargin;
			break;
		case MotionEvent.ACTION_UP:
			
			velocity.addMovement(event);

			// Toast.makeText(getActivity(), "X velocity on release: " + Float.toString(this.last_velocity), Toast.LENGTH_SHORT).show();
			int translate_x = (int) ((centerX - adjust) - bee.getX());
			int translate_y = (int) ((centerY - adjust) - bee.getY());
			Animation center_bee = new TranslateAnimation(0, translate_x, 0, translate_y);
			center_bee.setDuration(400);
			center_bee.setFillAfter(true);
			//bee.setAnimation(center_bee);
		
/*
			center_bee.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                	//bee.setY(centerY);
            		//bee.setX(centerX - x_adust);
             
                	view.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            */
			
			view.startAnimation(center_bee);
			
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		
			if((X < 0 || X >= width || Y < 0 || Y>= height))
				break;
			
			velocity.addMovement(event);
			layoutParams.leftMargin = X - _xDelta;
			layoutParams.topMargin = Y - _yDelta;
			layoutParams.rightMargin = -250;
			layoutParams.bottomMargin = -250;
			view.setLayoutParams(layoutParams);
			
		
			break;
		}
		root_layout.invalidate();
		return true;
	}
}
