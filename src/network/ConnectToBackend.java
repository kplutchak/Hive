package network;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import objects.Answer;
import objects.Question;
import objects.User;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hive.R;
import com.hive.fragments.SplashFragment;
import com.hive.main.ClientData;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

public class ConnectToBackend {
	
	//Contains public static functions
	
	public static void getAllQuestions(final Context mcontext){
		
		//Time started
		Calendar c = Calendar.getInstance(); 
		final long functionStartTime = c.get(Calendar.MILLISECOND);

		Ion.with(mcontext)
		.load("http://bhive.herokuapp.com/api/questions")

		.asJsonArray()
		.setCallback(new FutureCallback<JsonArray>() {
		   @Override
		    public void onCompleted(Exception e, JsonArray result) {
			   // this is called back onto the ui thread, no Activity.runOnUiThread or Handler.post necessary.
			   
			   
			   Log.d("ConnectToBackend", "getAllQuestions called");
               if (e != null) {
                   Toast.makeText(mcontext, "Error loading questions " + e.toString(), Toast.LENGTH_LONG).show();
                   Log.d("ConnectToBackend", e.toString());
                   return;
               }
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
                		   Answer newans = new Answer(answerBody, questionID, answerVotes);
                		   nq.addAnswer(newans);
                		  
            		   }
            		   
            		   ClientData.addQuestion(nq);
            	   }
            	   
            	   //Calculate how long it took do to this funciton so we know how much longer to display splashscreen for
            	   Calendar c = Calendar.getInstance(); 
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
		});
		return;
	}
	
	public static void getAllQuestionsByBlocking(final Context mcontext){
		
		JsonArray result;
		Log.d("ConnectToBackend", "getAllQuestionsBlocking called");
		try{
		result = Ion.with(mcontext).load("http://bhive.herokuapp.com/api/questions").asJsonArray().get();
		}
		catch (Exception e){
			 Toast.makeText(mcontext, "Error loading questions " + e.toString(), Toast.LENGTH_LONG).show();
             Log.d("ConnectToBackend", e.toString());
             return;
		}
		if (result!=null){
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
        		   Answer newans = new Answer(answerBody, questionID, answerVotes);
        		   nq.addAnswer(newans);
        		  
    		   }
    		   
    		   ClientData.addQuestion(nq);
    	   }
    	   
    	   return;
            	   
        }
	}
		
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
	
	
	
	
 
}
