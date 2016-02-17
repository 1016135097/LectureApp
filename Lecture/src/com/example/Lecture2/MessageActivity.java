package com.example.Lecture2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends Activity{
    private TextView msg;
	private Button back;
	   @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message_layout);
		back = (Button) findViewById(R.id.back);
		msg = (TextView) findViewById(R.id.message);
		Intent msgintent = getIntent();
		String msg1 = msgintent.getStringExtra("success");
		msg.setText(msg1);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mainintent = new Intent(MessageActivity.this,MainActivity.class);
				startActivity(mainintent);
				finish();
			}
		});
	}
	  
}
