package com.example.Lecture2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PublishActivity extends Activity {
	
	private EditText addTitle;
	private EditText addLectureCont;
	private EditText addLectureTime;
	private EditText addLecturePlace;
	private EditText addOthers;
	private TextView message;
	private Button messagebutton;
	Button btnsave;
	View view;
	String url = "http://172.23.81.1/JavaApp/JavaPostAPI.php";
	
	private static String msgsuccess = "发布成功";
	private static String Title;
	private static String LectureCont;
	private static String LectureTime;
	private static String LecturePlace;
	private static String Others;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.publishactivity_layout);
		addTitle = (EditText) findViewById(R.id.addTitle);
		addLectureCont = (EditText)findViewById(R.id.addLectureCont);
		addLectureTime = (EditText) findViewById(R.id.addLectureTime);
		addLecturePlace = (EditText) findViewById(R.id.addLecturePlace);
		addOthers = (EditText) findViewById(R.id.addOthers);
		btnsave = (Button) findViewById(R.id.ensurePublish);

    btnsave.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(addTitle.getText().toString().equals("")){
				Toast.makeText(PublishActivity.this, "error", 0).show();
			}
			else{
				Toast.makeText(PublishActivity.this, "successful", 0).show();
				Title = addTitle.getText().toString(); 
				LectureCont = addLectureCont.getText().toString(); 
				LectureTime = addLectureTime.getText().toString(); 
				LecturePlace = addLecturePlace.getText().toString(); 
				Others = addOthers.getText().toString(); 
				new Thread(publishDateRun).start();	
			}
			
		}
	});
}  
	
	 Runnable publishDateRun = new Runnable(){
	    	@Override
	    	public void run() {
	            HttpPost httpPost = new HttpPost(url);  
	            List<NameValuePair> params = new ArrayList<NameValuePair>(); 
	            Log.d("php","OK1");
	            params.add(new BasicNameValuePair("Title", Title));
	            params.add(new BasicNameValuePair("LectureCont", LectureCont));
	            params.add(new BasicNameValuePair("LectureTime", LectureTime));
	            params.add(new BasicNameValuePair("LecturePlace", LecturePlace));
	            params.add(new BasicNameValuePair("Others", Others));
	            Log.d("php","OK2");
	            HttpResponse httpResponse = null; 
	            try { 
	                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
	                httpResponse = new DefaultHttpClient().execute(httpPost); 
	                //System.out.println(httpResponse.getStatusLine().getStatusCode()); 
	                if (httpResponse.getStatusLine().getStatusCode() == 200) { 
	                    String result = EntityUtils.toString(httpResponse.getEntity());                    
	                    Log.d("php",result); 
	                    Intent publishintent = new Intent(PublishActivity.this,MessageActivity.class);
	                    publishintent.putExtra("success",msgsuccess);
	                    startActivity(publishintent);  
	                    finish();
	                } 
	            } catch (ClientProtocolException e) { 
	                e.printStackTrace(); 
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	            }   
	    	}
	    };
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent myIntent = new Intent();
			myIntent = new Intent(PublishActivity.this,MainActivity.class);
			startActivity(myIntent);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	} 
	
	}


