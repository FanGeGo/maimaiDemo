package com.maimai.demo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.maimai.demo.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/5.
 */
public class ImageAdatper extends ImageLoadAdatper {
	private ArrayList<String> mImgeList;
	private Context mContext;

	public ImageAdatper(final ArrayList<String> imgeList, final Context context) {
		mImgeList = imgeList;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void setImgeList(final ArrayList<String> imgeList) {
		mImgeList = imgeList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mImgeList.size();
	}

	@Override
	public Object getItem(final int position) {
		return mImgeList.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.image_item, null);
			viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.ivImage);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String url = mImgeList.get(position);

		if(!TextUtils.isEmpty(url) && url.equals("empty")) {
			viewHolder.mImageView.setImageBitmap(null);
			viewHolder.mImageView.setImageDrawable(null);
		}else{
			displayImage(viewHolder.mImageView, url);
		}


		return convertView;
	}

	private class ViewHolder {
		ImageView mImageView;
	}
}
