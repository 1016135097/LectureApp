package com.example.Lecture2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	
	private static String Id;
	private static String Title;
	private static String LectureCont;
	private static String LectureTime;
	private static String LecturePlace;
	private static String Others;
	private static String idLength;
	private static int idLengthNum;
	private static String result;
	private static int num1;
	public static final int UPDATE_TEXT = 1;
	public static final int REBUILD_TEXT = 2;
	
	String url = "http://172.23.81.1/JavaApp/JavaGetAPI.php";
	String url1 = "http://172.23.81.1/JavaApp/JavaGetIdAPI.php";
	
	private static ListView lectureLv;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();
	MyAdapter adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View home = inflater.inflate(R.layout.homefragment_layout, container, false);
		lectureLv =(ListView) home.findViewById(R.id.lectureListView);
		init in1 = new init();
		Log.d("shunxu","1");
		new Thread(in1).start();      //执行线程
		Log.d("Test1","Init Begin");
		adapter = new MyAdapter();
		lectureLv.setAdapter(adapter);
		Log.d("Test1","Init OFF");
		return home; 
	}
	
  	private Handler handler = new Handler(){ 
		public void handleMessage(Message msg){ 
			switch (msg.what){
			case REBUILD_TEXT:{
				adapter.notifyDataSetChanged();
				adapter = new MyAdapter(); 
				lectureLv.setAdapter(adapter);
	    		Log.d("shunxu","11");
				Log.d("Test1","Build OFF");
				break;
			}
			default:
				break;
			}
		}
	};
	
/*************************************************线程类定义*********************************************/
public class init implements Runnable{
	    	 
	    	@Override
	    	public void run(){	
				Log.d("shunxu","2");
	    		IdLengthSearch();
	    		num1=idLengthNum; 
	    		for(;num1>0;num1--)
	    		{
	    			Log.d("php","***********************************Start***********************************");
	    			IdPost(); 
					Log.d("shunxu","5");
	    			HashMap<String, Object> LvItem = new HashMap<String, Object>();
	    			LvItem.put("addId", Id);
		            LvItem.put("addTitle", Title);  
		            LvItem.put("addLectureCont", LectureCont); 
		            LvItem.put("addLecturePlace", LecturePlace);
		            LvItem.put("addLectureTime", LectureTime);
		            LvItem.put("addOthers", Others);  
		            listItem.add(LvItem);
		    		Log.d("shunxu","6");
					Log.d("Test1",Title);
		    		Log.d("shunxu","9");
					idLengthNum=idLengthNum-1;
					idLength = String.valueOf(idLengthNum); 
	    		}
	    		Log.d("shunxu","10");
	    		Message message =handler.obtainMessage();
				message.what = REBUILD_TEXT;	
				handler.sendMessage(message);
	    		Log.d("shunxu","12");
	    		Log.d("Test1","Run OFF");
	    	}  
	    	
	    	//id查询 
	    	public void IdLengthSearch(){
	    		HttpClient client = new DefaultHttpClient();
			    StringBuilder builder = new StringBuilder();
			    HttpGet myget = new HttpGet(url1);
			    try {
			    	HttpResponse response = client.execute(myget);
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					for (String s = reader.readLine(); s!= null; s= reader.readLine()) {
						builder.append(s);
					}
					Log.d("php",builder.toString());
					JSONObject jsonObject = new JSONObject(builder.toString());
					idLength = jsonObject.getString("IDLength"); 
					idLengthNum = Integer.parseInt(idLength);
					Log.d("shunxu","3");
					} catch (Exception e) {
						e.printStackTrace();
					}
			    Log.d("php","OK1");
	    	}   
	    	//id发送
	    	public void IdPost(){
	    		Log.d("shunxu","4");
	    		HttpPost httpPost = new HttpPost(url);  
	            List<NameValuePair> params = new ArrayList<NameValuePair>(); 
	            params.add(new BasicNameValuePair("idLength", idLength));
	            HttpResponse httpResponse = null; 
	            try { 
	                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
	                httpResponse = new DefaultHttpClient().execute(httpPost); 
	                //System.out.println(httpResponse.getStatusLine().getStatusCode()); 
	                if (httpResponse.getStatusLine().getStatusCode() == 200) { 
	                    result = EntityUtils.toString(httpResponse.getEntity());                    
	                    Log.d("php",result); 
	                } 
	                JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(result);
						Id = jsonObject.getString("Id");
						Title = jsonObject.getString("Title");
						LectureCont = jsonObject.getString("LectureCont");
						LectureTime = jsonObject.getString("LectureTime");
						LecturePlace = jsonObject.getString("LecturePlace");
						Others = jsonObject.getString("Others");
						Log.d("shunxu","4.5");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	            } catch (ClientProtocolException e) { 
	                e.printStackTrace(); 
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	            }
	            Log.d("php","OK2");
	    	}
	    }
	    
/*************************************************适配器类定义*********************************************/
public class MyAdapter extends  BaseAdapter{

	@Override
	public int getCount() {	
		Log.d("Test2","OKCont1");
		String LongTh = String.valueOf(listItem.size());
		Log.d("Test2",LongTh);
		return listItem.size();	
	}

	@Override
	public Object getItem(int position) {	
		Log.d("Test2","OKItem");
		return listItem.get(position);
	}

	@Override
	public long getItemId(int position) {	
		Log.d("Test2","OKItemId");
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		Log.d("Test2","OKView");
		ViewHolder vh = new ViewHolder();
		Log.d("Test", "getView " + position + " " + convertView);
		if(convertView==null){
			convertView = getLayoutInflater(null).inflate(R.layout.homefragment_item, null);
			vh.tvtitle = (TextView) convertView.findViewById(R.id.lecture_title);
			vh.tvcont = (TextView) convertView.findViewById(R.id.lecture_cont);
			vh.tvtime = (TextView) convertView.findViewById(R.id.lecture_time);
			vh.tvplace = (TextView) convertView.findViewById(R.id.lecture_place);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
				
		vh.tvtitle.setText(listItem.get(position).get("addTitle").toString());
		vh.tvcont.setText(listItem.get(position).get("addLectureCont").toString());
		vh.tvtime.setText(listItem.get(position).get("addLectureTime").toString());
     	vh.tvplace.setText(listItem.get(position).get("addLecturePlace").toString());
		
     	lectureLv.setOnItemClickListener(new OnItemClickListener() {
			@Override 
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				Intent itemactivity = new Intent(getActivity(),ItemActivity.class);
				itemactivity.putExtra("Id", listItem.get(position).get("addId").toString());
				itemactivity.putExtra("Title", listItem.get(position).get("addTitle").toString());
				itemactivity.putExtra("LectureCont", listItem.get(position).get("addLectureCont").toString());
				itemactivity.putExtra("LectureTime", listItem.get(position).get("addLectureTime").toString());
				itemactivity.putExtra("LecturePlace", listItem.get(position).get("addLecturePlace").toString());
				itemactivity.putExtra("Others", listItem.get(position).get("addOthers").toString());
				startActivity(itemactivity);
			}
		});
     	
		return convertView;
	}
}
		

/**********************************************************************************************/
public static class ViewHolder{
				
	TextView tvtitle;
	TextView tvcont;
	TextView tvtime;
	TextView tvplace;
	}

}
