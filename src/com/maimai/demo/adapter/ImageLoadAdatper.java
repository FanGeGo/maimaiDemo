package com.maimai.demo.adapter;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/5.
 */
public abstract class ImageLoadAdatper extends BaseAdapter {
	protected LayoutInflater mLayoutInflater;
	DisplayImageOptions options = null;

	public void displayImage(ImageView imageView, String url){
		if (options == null) {
			options = new DisplayImageOptions.Builder()
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.build();
		}

		ImageLoader.getInstance().displayImage(url, imageView, options);
	}

}
