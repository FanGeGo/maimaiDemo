package com.maimai.demo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.maimai.demo.R;
import com.maimai.demo.adapter.SimplePagerAdapter;
import com.maimai.demo.fragment.DynamicFragment;
import com.maimai.demo.fragment.GossipFragment;
import com.maimai.demo.util.Utils;
import com.maimai.demo.widget.TabWithLineView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{
	private TabWithLineView mTabDynamic;
	private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
	private TabWithLineView mTabBagua;
	private ViewPager viewPager;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupViews();
	}

	private void setupViews() {
		mTabDynamic = (TabWithLineView) findViewById(R.id.tabDynamic);
		mTabBagua = (TabWithLineView) findViewById(R.id.tabBagua);
		mTabBagua.setOnClickListener(this);
		mTabDynamic.setOnClickListener(this);

		findViewById(R.id.btnEdit).setOnClickListener(this);
		findViewById(R.id.btnSearch).setOnClickListener(this);

		mFragments.add(new DynamicFragment());
		mFragments.add(new GossipFragment());
		SimplePagerAdapter adapter = new SimplePagerAdapter(getSupportFragmentManager(), mFragments);

		viewPager = (ViewPager) findViewById(R.id.vpPager);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onClick(final View v) {
		int id = v.getId();

		switch (id){
			case R.id.tabBagua:
				updateTab(1);
				viewPager.setCurrentItem(1);
				break;

			case R.id.tabDynamic:
				updateTab(0);
				viewPager.setCurrentItem(0);

				break;

			case R.id.btnSearch:
				Utils.showToast(this, "搜索");

				break;

			case R.id.btnEdit:
				Utils.showToast(this, "编辑");
				break;
		}
	}

	private void updateTab(int position){
		if(position == 0){
			mTabDynamic.select(true);
			mTabBagua.select(false);
		}else{
			mTabDynamic.select(false);
			mTabBagua.select(true);
		}
	}

	@Override
	public void onPageScrolled(final int i, final float v, final int i1) {
	}

	@Override
	public void onPageSelected(final int i) {
		updateTab(i);

	}

	@Override
	public void onPageScrollStateChanged(final int i) {

	}
}
