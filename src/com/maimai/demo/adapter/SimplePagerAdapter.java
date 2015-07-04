package com.maimai.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

public class SimplePagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	private FragmentManager fragmentManager;

	public SimplePagerAdapter(final FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
		this.fragmentManager = fm;
	}

	@Override
	public Fragment getItem(final int i) {
		return fragments.get(i);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(List<Fragment> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commitAllowingStateLoss();
			fragmentManager.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}
}