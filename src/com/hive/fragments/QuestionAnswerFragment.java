package com.hive.fragments;


import objects.Answer;
import objects.Question;
import objects.User;
import network.ConnectToBackend;

import com.hive.R;
import com.hive.animation.SwipeDetector;
import com.hive.helpers.Constants;
import com.hive.main.MainActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
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
	 
	 private int screen_height;
	 private int screen_width;
	 
	 private boolean has_been_moved = false;
	 private float _xDelta;
	 private float _yDelta;
	 private ImageView bee;
	 private ViewGroup root_layout;
	 private RelativeLayout create_question;

	 
	 private View horizontalLine;
	// TextViews
	
	private TextView question;
	private TextView a1;
	private TextView a2;
	
	public QuestionAnswerFragment() {
		super();
	
		// TODO Auto-generated constructor stub
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
	
	public class Waiter implements Runnable {

		String message;
		VerticalLine vl;

		public Waiter(String message) {
			this.message = message;
			this.vl = new VerticalLine(getActivity());
		}

		@Override
		public void run() {
			synchronized (message) {
				try {
					Log.d("Threads", "Waiter is waiting!");
					message.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			   handler.post(new Runnable(){
                   public void run() {
                	   root_layout.addView(vl);
                	   //root_layout.addView(bee);
               }
           });
			   
			   
			Log.d("Threads", "Waiter is done waiting!");
		}

	}
	
	public class Notifier implements Runnable {

		String message;
		HorizontalLine hl;

		public Notifier(String message) {
			this.message = message;
			this.hl = new HorizontalLine(getActivity());
		}

		@Override
		public void run() {
		//ConnectToBackend.postQuestion(getActivity(), q);

		
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			   handler.post(new Runnable(){
                   public void run() {
                	   root_layout.addView(hl);
               }
           });
			
			
			while(!this.hl.halfwayDone)
			{
				// do nothing, short animation so not a big deal
			}
			
			synchronized (message) {
				Log.d("Threads", "Notifier is notifying waiting thread to wake up!");
				message.notify();
			}

		}

	}

    public static class HorizontalLine extends View {

        private static Paint paint;
        private int screenW, screenH;
        private float X, Y;
        private Path path;
        private float initialScreenW;
        private float initialX, plusX;
        private float TX;
        private boolean translate;
        private int flash;
        private Context context;
        public boolean halfwayDone;


        public HorizontalLine (Context context) {
            super(context);

            this.context=context;
            this.halfwayDone = false;

            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(4);
            paint.setAntiAlias(true);
           // paint.setStrokeCap(Paint.Cap.ROUND);
            //paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            //paint.setShadowLayer(7, 0, 0, Color.RED);

            path= new Path();
            TX=0;
            translate=true;

            flash=0;

        }

        @Override
        public void onSizeChanged (int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            screenW = w;
            screenH = h;
            X = 0;
            Y = 100;

            initialScreenW=screenW;
            initialX=((screenW/2)+(screenW/4));
            plusX=(screenW/24);
            
            

            path.moveTo(X, Y);

        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //canvas.save();    

            path.lineTo(X,Y);
            //canvas.translate(-TX, 0);
            //if(translate==true)
            //{
               // TX+=4;
            //}

            if(X<screenW)
            {
                X+=20;
            }
            if(X>=(screenW/2))
            {
            	this.halfwayDone = true;
            }

            canvas.drawPath(path, paint);


            //canvas.restore(); 

            invalidate();
        }
    }
    
    public static class VerticalLine extends View {

        private static Paint paint;
        private int screenW, screenH;
        private float X, Y;
        private Path path;
        private float initialScreenW;
        private float initialX, plusX;
        private float TX;
        private boolean translate;
        private int flash;
        private Context context;


        public VerticalLine (Context context) {
            super(context);

            this.context=context;

            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(4);
            paint.setAntiAlias(true);
           // paint.setStrokeCap(Paint.Cap.ROUND);
            //paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            //paint.setShadowLayer(7, 0, 0, Color.RED);

            path= new Path();
            TX=0;
            translate=true;

            flash=0;

        }

        @Override
        public void onSizeChanged (int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            screenW = w;
            screenH = h;
            X = w/2;
            Y = 100;

            initialScreenW=screenW;
            initialX=((screenW/2)+(screenW/4));
            plusX=(screenW/24);

            path.moveTo(X, Y);

        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //canvas.save();    

            path.lineTo(X,Y);
            //canvas.translate(-TX, 0);
            //if(translate==true)
            //{
               // TX+=4;
            //}

            if(Y<screenH)
            {
                Y+=20;
            }

            canvas.drawPath(path, paint);


            //canvas.restore(); 

            invalidate();
        }
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	//if(savedInstanceState != null)
    		//return
    	
    	
        // inflate the layout
    	View v = inflater.inflate(R.layout.questionanswer_fragment, container, false);

 	   // Gesture detection
        gestureDetector = new GestureDetector(getActivity(), new SwipeDetector(getActivity()));
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        create_question = (RelativeLayout) v.findViewById(R.id.create_question_button);
        
        create_question.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MainActivity ma = (MainActivity) getActivity();
	 			if(ma.getShowingFragmentID().equals(Constants.QUESTION_ANSWER_FRAGMENT_ID))
	 				ma.switchToFragment(Constants.CREATE_QUESTION_FRAGMENT_ID);
			}
        	
        });
        
    	root_layout = (ViewGroup) v.findViewById(R.id.root_qa);
    	
    	// lines
    	//HorizontalLine hl = new HorizontalLine(v.getContext());
    	//root_layout.addView(hl);
    	
    	//VerticalLine vl = new VerticalLine(v.getContext());
    	//root_layout.addView(vl);
    	
    	
		bee = new ImageView(getActivity());
		bee.setImageDrawable(getResources().getDrawable(R.drawable.circle_chooser));

		DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        int px = (int) Math.ceil(30 * logicalDensity);
		
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(px, px);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        screen_height = size.y;
        screen_width = size.x;
        
        centerY=screen_height/2;
        centerX=screen_width/2;

        adjust = (int) Math.ceil(15 * logicalDensity);
        
		bee.setLayoutParams(layoutParams);
		bee.setOnTouchListener(this);
		
		bee.setY(centerY - adjust);
		bee.setX(centerX - adjust);
		
		

    	final View fv = v;
 
    	question = (TextView) fv.findViewById(R.id.question_text);
    	
    	question.setOnTouchListener(new View.OnTouchListener() { 
            @Override
           public boolean onTouch(View v, MotionEvent event){
                return gestureDetector.onTouchEvent(event);
           }
    	});
    	
    	int textHeight = question.getHeight();
    	int textWidth = question.getWidth();
    	//question.setX(centerX - (textWidth/2));
    	//question.setY(centerY - (textHeight/2));
    	
		a1 = (TextView) fv.findViewById(R.id.answer_one);
		a2 = (TextView) fv.findViewById(R.id.answer_two);
		

        Runnable runnable = new Runnable() {
     	   @Override
     	   public void run() {

				final Animation animationFadeIn = AnimationUtils.loadAnimation(fv.getContext(),
				         android.R.anim.fade_in);

					//question.startAnimation(animationFadeIn);
					a1.startAnimation(animationFadeIn);
					a2.startAnimation(animationFadeIn);
					
					//question.setVisibility(View.VISIBLE);
					a1.setVisibility(View.VISIBLE);
					a2.setVisibility(View.VISIBLE);
   
     	   }
     	};
     	
        Runnable runnable_fade_question = new Runnable() {
      	   @Override
      	   public void run() {

      		 AnimationSet set = new AnimationSet(true);
      	
      	     Animation anim = new AlphaAnimation(1.0f, 0.0f);
      	     anim.setDuration(500);
      	     anim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation arg0) {
					question.setVisibility(View.INVISIBLE);
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					
				}

				@Override
				public void onAnimationStart(Animation animation) {
					
				}
      	    	 
      	     });
      	     set.addAnimation(anim); 
 					
      	     question.startAnimation(set);
      	     
    
      	   }
      	};

        Runnable runnable_move_and_fade = new Runnable() {
       	   @Override
       	   public void run() {

       	     AnimationSet set = new AnimationSet(true);
       	     Animation trAnimation = new TranslateAnimation(0, 0, 0, centerY + 100 - screen_height);
       	     trAnimation.setDuration(1000);
       	     trAnimation.setFillAfter(true);

       	     set.addAnimation(trAnimation);
       	     Animation anim = new AlphaAnimation(0.0f, 1.0f);
       	     anim.setDuration(1000);
       	     anim.setFillAfter(true);
       	     set.addAnimation(anim); 
       	     set.setFillAfter(true);
       	     
     	     anim.setAnimationListener(new AnimationListener() {

 				@Override
 				public void onAnimationEnd(Animation arg0) {
 					int textHeight = question.getHeight();
 					//question.setY(centerY-500 - textHeight);
 					question.setVisibility(View.VISIBLE);
 					//question.clearAnimation();
 					
 				}

 				@Override
 				public void onAnimationRepeat(Animation animation) {
 					
 				}

 				@Override
 				public void onAnimationStart(Animation animation) {
 					
 				}
       	    	 
       	     });

       	     question.startAnimation(set);
     
       	   }
       	};
       	
       /*	Runnable horizontal_line = new Runnable() {

			@Override
			public void run() {
			 	HorizontalLine hl = new HorizontalLine(getView().getContext());
		    	root_layout.addView(hl);
		    	this.notify();
				
			}
       		
       	};
       	
      	Runnable vertical_line = new Runnable() {

			@Override
			public void run() {
				
		    	try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	VerticalLine vl = new VerticalLine(getView().getContext());
		    	root_layout.addView(vl);
				
			}
       		
       	};
*/
     handler.postDelayed(runnable_fade_question, 500);
     handler.postDelayed(runnable_move_and_fade, 1000);
     
     String message = "Lines";

     Waiter waiter = new Waiter(message);
		Thread waiterThread = new Thread(waiter, "waiterThread");
		waiterThread.start();

		Notifier notifier = new Notifier(message);
		Thread notifierThread = new Thread(notifier, "notifierThread");
		notifierThread.start();


    	
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
		
		float X = event.getRawX();
		float Y = event.getRawY();
		
	
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			velocity.addMovement(event);
			
			_xDelta = X - (view.getX());
			_yDelta = Y - (view.getY());
			break;
		case MotionEvent.ACTION_UP:
			
			velocity.addMovement(event);

			float destination_x = 0;
			float destination_y = 0;
			
			 Toast.makeText(getActivity(), "X velocity on release: " + Float.toString(this.last_velocity), Toast.LENGTH_SHORT).show();
			 if(this.last_velocity < -800 || view.getX() < (this.centerX/2))
			 {
				 destination_x = this.centerX/2 - this.adjust;
				 destination_y = this.centerY;
			 }
			 else if(this.last_velocity > 800 || view.getX() > (3 * (this.centerX/2)))
			 {
				 destination_x = (3 * this.centerX/2) - this.adjust;
				 destination_y = this.centerY;
			 }
			 else
			 {
				 destination_x = centerX - adjust;
				 destination_y = centerY - adjust;
			 }
			 
			 final float dest_x = destination_x;
			 final float dest_y = destination_y;
				 
			float translate_x = (destination_x) - view.getX();
			float translate_y = (destination_y) - view.getY();
			Animation center_bee = new TranslateAnimation(0, translate_x, 0, translate_y);
			center_bee.setDuration(400);
			center_bee.setFillAfter(true);
			//center_bee.setFillBefore(true);
			//bee.setAnimation(center_bee);
		

			center_bee.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                	
                	view.setX(dest_x);
        		    view.setY(dest_y);
             
                	view.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            
			
			view.startAnimation(center_bee);
			
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			velocity.addMovement(event);
			//if((X < 0 || X >= width || Y < 0 || Y>= height))
				//break;

			view.setX(X - _xDelta);
			view.setY(Y - _yDelta);
			
			velocity.computeCurrentVelocity(1000);
			
			float overall_velocity = (float) Math.hypot(velocity.getXVelocity(), velocity.getYVelocity());
			this.last_velocity = velocity.getXVelocity();
		
			break;
		}
		root_layout.invalidate();
		return true;
	}
}
