package com.maimai.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.maimai.demo.R;
import com.maimai.demo.adapter.GossipAdatper;
import com.maimai.demo.bean.GossipInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/6.
 */
public class GossipFragment extends Fragment {
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_page, container, false);
		ListView listView = (ListView) view.findViewById(R.id.llMainList);
		listView.setAdapter(new GossipAdatper(getActivity(), createList()));
		return view;
	}

	private ArrayList<GossipInfo> createList(){
		ArrayList<GossipInfo> list = new ArrayList<GossipInfo>();
		list.add(createGossipInfo1());
		list.add(createGossipInfo2());
		list.add(createGossipInfo3());
		list.add(createGossipInfo1());
		list.add(createGossipInfo2());
		list.add(createGossipInfo3());
		return list;
	}


	private GossipInfo createGossipInfo1(){
		GossipInfo gossipInfo = new GossipInfo();
		gossipInfo.content = "今天在台湾召开的Computex大会上，戴尔同样携多款新品亮相，其中包含两款3000系列的桌面一体机、一台运行Windows 8.1系统Inspiron Micro桌面微型主机、Nextbook Flexx 11/10两款入门二合一设备和全新的 \" " +
				"Inspiron 5000系列。";
		return gossipInfo;
	}

	private GossipInfo createGossipInfo2(){
		GossipInfo gossipInfo = new GossipInfo();
		gossipInfo.content = "今天在台湾召开的Computex大会上，戴尔同样携多款新品亮相，其中包含两款3000系列的桌面一体机、一台运行Windows 8.1系统Inspiron Micro桌面微型主机、Nextbook Flexx 11/10两款入门二合一设备和全新的 \" " +
				"Inspiron 5000系列。";
		gossipInfo.imgList.add("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s240-c/A%252520Photographer.jpg");
		return gossipInfo;
	}


	private GossipInfo createGossipInfo3(){
		GossipInfo gossipInfo = new GossipInfo();
		gossipInfo.content = "还能不能愉快的搬砖了";
		return gossipInfo;
	}
}
