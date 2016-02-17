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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ItemActivity extends Activity implements OnClickListener{

	private static String Id;
	private static String Title;
	private static String LectureCont;
	private static String LectureTime;
	private static String LecturePlace;
	private static String Others;
	
	private static TextView TitleView;
	private static TextView LectureContView;
	private static TextView LectureTimeView;
	private static TextView LecturePlaceView;
	private static TextView OthersView;
	private static Button signUpButton;
	private static Button checkInButton;
	private static String msgsuccess = "报名成功";
	private static String cisuccess = "恭喜！您已经成功签到！";
	
	String url = "http://172.23.81.1/JavaApp/JavaSignUpAPI.php";
	String url2 = "http://172.23.81.1/JavaApp/JavaCheckInAPI.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.item_onclick_layout);
		TitleView = (TextView) findViewById(R.id.item_title);
		LectureContView = (TextView) findViewById(R.id.item_lecture_cont);
		LectureTimeView = (TextView) findViewById(R.id.item_lecture_time);
		LecturePlaceView = (TextView) findViewById(R.id.item_lecture_place);
		OthersView = (TextView) findViewById(R.id.item_others);
		signUpButton = (Button) findViewById(R.id.sign_up_button);
		checkInButton = (Button) findViewById(R.id.check_in_button);
		
		signUpButton.setOnClickListener(this);
		checkInButton.setOnClickListener(this);
		Intent dataIntent=getIntent();
        Bundle bundle=dataIntent.getExtras();
        Id = bundle.getString("Id");
        Title = bundle.getString("Title");
        LectureCont = bundle.getString("LectureCont");
        LectureTime = bundle.getString("LectureTime");
        LecturePlace = bundle.getString("LecturePlace");
        Others = bundle.getString("Others");
        
        Log.d("Item",Id);
        Log.d("Item",Title);
        Log.d("Item",LectureCont);
        Log.d("Item",LectureTime);
        Log.d("Item",LecturePlace);
        Log.d("Item",Others);
        
        TitleView.setText(Title);
        LectureContView.setText(LectureCont);
        LectureTimeView.setText(LectureTime);
        LecturePlaceView.setText(LecturePlace);
        OthersView.setText(Others);
       
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.sign_up_button:{
			Log.d("Item","OKClick1");
			new Thread(signUpRun).start();	
			Log.d("Item","OKClick2");
			break;
		}
		case R.id.check_in_button:{
			new Thread(checkInRun).start();
			break;
		}
		default:break;
		}
	}
	
	Runnable signUpRun = new Runnable(){
    	@Override
    	public void run() {
            HttpPost httpPost = new HttpPost(url);  
            List<NameValuePair> params = new ArrayList<NameValuePair>(); 
            Log.d("php","OK1");
            params.add(new BasicNameValuePair("Id", Id));
            Log.d("php","OK2");
            HttpResponse httpResponse = null; 
            try { 
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
                httpResponse = new DefaultHttpClient().execute(httpPost); 
                //System.out.println(httpResponse.getStatusLine().getStatusCode()); 
                if (httpResponse.getStatusLine().getStatusCode() == 200) { 
                    String result = EntityUtils.toString(httpResponse.getEntity());                    
                    Log.d("php",result); 
                    Intent signUpIntent = new Intent(ItemActivity.this,MessageActivity.class);
                    signUpIntent.putExtra("success",msgsuccess);
                    startActivity(signUpIntent);  
                    finish();
                } 
            } catch (ClientProtocolException e) { 
                e.printStackTrace(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            }   
    	}
    };
    Runnable checkInRun = new Runnable(){
    	@Override
    	public void run() {
            HttpPost httpPost = new HttpPost(url2);  
            List<NameValuePair> params = new ArrayList<NameValuePair>(); 
            Log.d("php","OK1");
            params.add(new BasicNameValuePair("Id", Id));
            Log.d("php","OK2");
            HttpResponse httpResponse = null; 
            try { 
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
                httpResponse = new DefaultHttpClient().execute(httpPost); 
                //System.out.println(httpResponse.getStatusLine().getStatusCode()); 
                if (httpResponse.getStatusLine().getStatusCode() == 200) { 
                    String result = EntityUtils.toString(httpResponse.getEntity());                    
                    Log.d("php",result); 
                    Intent checkInIntent = new Intent(ItemActivity.this,MessageActivity.class);
                    checkInIntent.putExtra("success",cisuccess);
                    startActivity(checkInIntent);  
                    finish();
                } 
            } catch (ClientProtocolException e) { 
                e.printStackTrace(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            }   
    	}
    };
	
}
