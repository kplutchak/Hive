package network;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import objects.Answer;
import objects.Question;
import objects.User;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hive.R;
import com.hive.fragments.SplashFragment;
import com.hive.helpers.Constants;
import com.hive.main.ClientData;
import com.hive.main.MainActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

public class ConnectToBackend {
	
	//Contains public static functions
	
	public static void getAllQuestions(final Context mcontext){
		
		 Log.d("ConnectToBackend", "starting getAllQuestions");
		 
		//Time started
		Calendar c = Calendar.getInstance(); 
		final long functionStartTime = c.get(Calendar.MILLISECOND);
		final String url = "http://bhive.herokuapp.com/api/questions";
		MainActivity ma = (MainActivity) mcontext;
		
		ProgressBar mProgress = (ProgressBar) ma.findViewById(R.id.progress_bar);
		 
		 
		ProgressBar progressBar = new ProgressBar(mcontext);
		progressBar.setIndeterminate(true);
		ProgressDialog progressDialog = new ProgressDialog(mcontext);
		
		JsonArray result = new JsonArray();
		try{
		result = Ion.with(mcontext)
		//Ion.with(mcontext)
		.load(url)
		.progressBar(mProgress)
		.progressHandler(new ProgressCallback() {
                        @Override
                        public void onProgress(long downloaded, long total) {
                            Log.d("ConnectToBackend", "" + downloaded + " / " + total);
                        }
                    })
	//	.progressDialog(progressDialog)
		.asJsonArray()/*
		.setCallback(new FutureCallback<JsonArray>() {
		   @Override
		    public void onCompleted(Exception e, JsonArray result) {
			   // this is called back onto the ui thread, no Activity.runOnUiThread or Handler.post necessary.
			   */
		.get();
		}catch(Exception e){
			   Log.d("ConnectToBackend", "getAllQuestions called with url " + url);
               if (e != null) {
                   Toast.makeText(mcontext, "Error loading questions " + e.toString(), Toast.LENGTH_LONG).show();
                   Log.d("ConnectToBackend", e.toString());
                   return;
               }
		}
		if(false){}
               else{
            	   Log.d("connectToBackend", "Received JSON array for getAllQuestions");
            	//   Log.d("connectToBackend", "jsonObject is " + result.toString());
            	   
            	   
            	   List<JsonObject> jsonlist = new ArrayList<JsonObject>();
            	   
            	   
            	   if (result != null) { 
         		      for (int i = 0; i < result.size(); i++) {
                        jsonlist.add(result.get(i).getAsJsonObject());
         		      } 
            	   }
            	   
            	   for (int k = 0; k < jsonlist.size(); k++){
            		   //Get text body, user of question
            		   String questionbody = jsonlist.get(k).get("text").getAsString();
            		   String userID = jsonlist.get(k).get("user_id").getAsString();
            		   User tempuser = new User(userID);
            		   Question nq = new Question(questionbody, tempuser);
            		 
            		   
            		   //Get Answers of question
            		   JsonArray answerJson = jsonlist.get(k).get("answers").getAsJsonArray();
            		   List<JsonObject> answerJsonList = new ArrayList<JsonObject>();
            		   for (int i = 0; i < answerJson.size(); i++) {
                           answerJsonList.add(answerJson.get(i).getAsJsonObject());
            		      } 
            		   
            		   for (int j = 0; j < answerJsonList.size(); j++){
            			   String answerBody = answerJsonList.get(j).get("text").getAsString();
                		   String questionID = answerJsonList.get(j).get("id").getAsString();
                		   int answerVotes = answerJsonList.get(j).get("votes").getAsInt();
                		   Answer newans = new Answer(answerBody);
                		   newans.setVotes(answerVotes);
                		   newans.setQuestionID(questionID);
                		   nq.addAnswer(newans);
                		   
                		  
            		   }
            		   
            		   ClientData.addQuestion(nq);
            	   }
            	   
            	   //Calculate how long it took do to this funciton so we know how much longer to display splashscreen for
            	 //  Calendar c = Calendar.getInstance(); 
            	   /*
           		   long functionEndTime = c.get(Calendar.MILLISECOND);
           		   long totaltime = functionEndTime - functionStartTime;
           		   Log.d("ConnectToBackend", "functionEndTime - funcitonStartTime = " + Long.toString(totaltime));
           		   long wait_time = SplashFragment.SPLASH_MIN_WAITTIME - totaltime;
           		   if (wait_time<0){
           			   wait_time=0;
           		   }*/

            	  // sf.endSplashScreen(SplashFragment.SPLASH_MIN_WAITTIME);
            	   return;
            	   
               }
               // add the tweets
               //Execute future callback
		    }
		//});
		//return;
	//}
	
	
		
	public static void newQuestion(final Context mcontext, Question mquestion){
		JsonObject json = new JsonObject();
		json.addProperty("foo", "bar");

		Ion.with(mcontext)
		.load("bhive.herokuapp.com/api/questions")
		.setJsonObjectBody(json)
		.asJsonObject()
		.setCallback(new FutureCallback<JsonObject>() {
		   @Override
		    public void onCompleted(Exception e, JsonObject result) {
		        // do stuff with the result or error
		    }
		});
	}
	
	//ToDo : figure out how to post!
	public static void answerQuestion(final Context mcontext, Answer providedAnswer){
		JsonObject json = new JsonObject();
		json.addProperty("vote", "true");
		final String url = "http://bhive.herokuapp.com/api/answers/"+providedAnswer.getQuestionID()+"/";
		Ion.with(mcontext)
		.load("PUT", url)
		.setJsonObjectBody(json)
		.asJsonObject()
		.setCallback(new FutureCallback<JsonObject>() {
		   @Override
		    public void onCompleted(Exception e, JsonObject result) {
			   if (e != null){
				   Log.d("ConnectToBackend", "answerquestions called with "+url+" and has error" + e.toString());
				   if (result==null){
					   Log.d("ConnectToBackend", "answerQuestions returns result with NULL");
				   } 
			   }
			   
			   else{
			   Log.d("ConnectToBackend", "answerQuestion asked with url " + url + " : and result " + result.toString());
			   }
		        
		    }
		});
	}
	
	public static void postQuestion(final Context mcontext, Question mquestion){
		JsonArray answerarray = new JsonArray();
		JsonObject answerjson = new JsonObject();
		JsonObject answerjson2 = new JsonObject();
		answerjson.addProperty("text", mquestion.getChoices().get(0).getAnswerBody());
		answerjson2.addProperty("text", mquestion.getChoices().get(1).getAnswerBody());
		answerarray.add(answerjson);
		answerarray.add(answerjson2);
		
		JsonObject json = new JsonObject();
		json.addProperty("text", mquestion.getQuestionBody());
		json.add("answers", answerarray);
		json.addProperty("user_id", "temp user id");
		final String url = "http://bhive.herokuapp.com/api/questions/";
		Ion.with(mcontext)
		.load(url)
		.setJsonObjectBody(json)
		.asJsonObject()
		.setCallback(new FutureCallback<JsonObject>() {
		   @Override
		    public void onCompleted(Exception e, JsonObject result) {
			   if (e != null){
				   Log.d("ConnectToBackend", "postQuestion called with "+url+" and has error" + e.toString());
				   if (result==null){
					   Log.d("ConnectToBackend", "postQuestion returns result with NULL");
				   } 
				   Toast.makeText(mcontext, "Error submitting question " + e.toString(), Toast.LENGTH_LONG).show();  
			   }
			   else{
				   Toast.makeText(mcontext, "Sucessfully posted question", Toast.LENGTH_LONG).show();
				   Log.d("ConnectToBackend", "postQuestion asked with url " + url + " : and result " + result.toString());
				   
				   //Switch to questions fragment
					MainActivity ma = (MainActivity) mcontext;
						if(ma.getShowingFragmentID().equals(Constants.CREATE_QUESTION_FRAGMENT_ID))
							ma.switchToFragment(Constants.QUESTION_ANSWER_FRAGMENT_ID);
			   }
		        
		    }
		});
	}
	
	
	
	
 
}
