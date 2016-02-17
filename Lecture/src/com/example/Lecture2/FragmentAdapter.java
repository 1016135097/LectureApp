package com.example.Lecture2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{
	public final static int TAB_COUNT = 2;
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int id) {
		switch (id) {
		case MainActivity.TAB_HOMEFRAGMENT:
			HomeFragment home_page = new HomeFragment();
			return home_page;
			
/*		case MainActivity.TAB_CLASSIFYFRAGMENT:
			ClassifyFragment classify_page = new ClassifyFragment();
			return classify_page;
						
		case MainActivity.TAB_POPULARFRAGMENT:
			PopularFragment popular_page = new PopularFragment();
			return popular_page;*/
			
		case MainActivity.TAB_MINEFRAGMENT:
			MineFragment mine_page = new MineFragment();
			return mine_page;
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}
}
