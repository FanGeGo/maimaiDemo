
package com.maimai.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.maimai.demo.R;
import com.maimai.demo.bean.Emojicon;
import com.maimai.demo.widget.emojicon.EmojiconTextView;

public class EmojiAdapter extends BaseAdapter {
	private boolean mUseSystemDefault = false;
	private Emojicon[] data;
	private LayoutInflater mLayoutInflater;

	public EmojiAdapter(Context context, Emojicon[] data) {
		mUseSystemDefault = false;
		this.data = data;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(final int position) {
		return data[position];
	}

	@Override
	public long getItemId(final int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.emojicon_item, null);
			ViewHolder holder = new ViewHolder();
			holder.icon = (EmojiconTextView) v.findViewById(R.id.emojicon_icon);
			holder.icon.setUseSystemDefault(mUseSystemDefault);
			v.setTag(holder);
		}
		Emojicon emoji = data[position];
		ViewHolder holder = (ViewHolder) v.getTag();
		holder.icon.setText(emoji.getEmoji());
		return v;
	}

	private class ViewHolder {
		EmojiconTextView icon;
	}
}