package com.example.Lecture2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	public static final int TAB_HOMEFRAGMENT = 0;
	public static final int TAB_CLASSIFYFRAGMENT = 3;
	public static final int TAB_POPULARFRAGMENT = 2;
	public static final int TAB_MINEFRAGMENT = 1;
	public static final int REQUSET = 1;
	private ViewPager viewPager;
	private RadioButton main_tab_home, main_tab_classify, main_tab_publish, main_tab_popular, main_tab_mine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);		
		initView();
		addListener();
		
	}
	
	//-----------------------------Activity管理Fragment滑动设置------------------------------------
		//视图初始化
		public void initView() {	
			
			main_tab_home = (RadioButton) findViewById(R.id.home_button);
			/*main_tab_classify = (RadioButton) findViewById(R.id.classify_button);*/
			main_tab_publish = (RadioButton) findViewById(R.id.publish_button);
	/*		main_tab_popular = (RadioButton) findViewById(R.id.popular_button);*/
			main_tab_mine = (RadioButton) findViewById(R.id.mine_button);
			
			main_tab_home.setOnClickListener(this);
			/*main_tab_classify.setOnClickListener(this);*/
			main_tab_publish.setOnClickListener(this);
			/*main_tab_popular.setOnClickListener(this);*/
			main_tab_mine.setOnClickListener(this);
					
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			
			FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
			viewPager.setAdapter(adapter);		
		}	
		//添加监听器
		public void addListener() {
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int id) {
					switch (id) {
					case TAB_HOMEFRAGMENT:
						main_tab_home.setChecked(true);
						break;
/*					case TAB_CLASSIFYFRAGMENT:
						main_tab_classify.setChecked(true);
						break;
					case TAB_POPULARFRAGMENT:
						main_tab_popular.setChecked(true);
						break;*/
					case TAB_MINEFRAGMENT:
						main_tab_mine.setChecked(true);
						break;
					default:
						break;
					}
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}
				@Override
				public void onPageScrollStateChanged(int arg0) {}
			});
		}
		//------------------------------------------------------------------------------------------

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			switch (v.getId()) {
			case R.id.home_button:
				viewPager.setCurrentItem(TAB_HOMEFRAGMENT);
				break;
/*			case R.id.classify_button:
				viewPager.setCurrentItem(TAB_CLASSIFYFRAGMENT);
				break;
			case R.id.popular_button:
				viewPager.setCurrentItem(TAB_POPULARFRAGMENT);
				break;*/
			case R.id.mine_button:
				viewPager.setCurrentItem(TAB_MINEFRAGMENT);
				break;
			case R.id.publish_button:
				Intent publishIntent =new Intent(MainActivity.this, PublishActivity.class);
				startActivityForResult(publishIntent, REQUSET);
				finish();
				break;

			default:
				break;
			}		
			
		}
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == MainActivity.REQUSET ){
//				Toast.makeText(this, data.getStringExtra("TITLE"), 1).show();
			}
		}
		
}
