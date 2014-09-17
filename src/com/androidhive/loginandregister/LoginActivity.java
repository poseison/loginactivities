package com.androidhive.loginandregister;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	ProgressDialog prgDialog;
    // Error Msg TextView Object
   
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
    }
    
    
    public void loginUser(View view){
    	
    	 String email = "chlaliotis@gmail.com";
         String password = "xlal1779";
         // Instantiate Http Request Param Object
         RequestParams params = new RequestParams();
         params.put("username", email);
         // Put Http parameter password with value of Password Edit Value control
         params.put("password", password);
         // Invoke RESTful Web Service with Http parameters
         invokeWS(params);
    	
    	
    }
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
         prgDialog.show();
         // Make RESTful webservice call using AsyncHttpClient object
         AsyncHttpClient client = new AsyncHttpClient();
         client.get("http://192.168.2.2:9999/useraccount/login/dologin",params ,new AsyncHttpResponseHandler() {
             // When the response returned by REST has Http response code '200'
             @Override
             public void onSuccess(String response) {
                 // Hide Progress Dialog
                 prgDialog.hide();
                 try {
                          // JSON Object
                         JSONObject obj = new JSONObject(response);
                         // When the JSON response has status boolean value assigned with true
                         if(obj.getBoolean("status")){
                             Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                             // Navigate to Home screen
                           //  navigatetoHomeActivity();
                         } 
                         // Else display error message
                         else{
                         //    errorMsg.setText(obj.getString("error_msg"));
                             Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                         }
                 } catch (JSONException e) {
                     // TODO Auto-generated catch block
                     Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                     e.printStackTrace();
 
                 }
             }
             // When the response returned by REST has Http response code other than '200'
             @Override
             public void onFailure(int statusCode, Throwable error,
                 String content) {
                 // Hide Progress Dialog 
                 prgDialog.hide();
                 // When Http response code is '404'
                 if(statusCode == 404){
                     Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                 } 
                 // When Http response code is '500'
                 else if(statusCode == 500){
                     Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                 } 
                 // When Http response code other than 404, 500
                 else{
                     Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                 }
             }
         });
    }
 
}