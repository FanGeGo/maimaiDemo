package com.maimai.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.maimai.demo.R;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/2.
 */
public class TabWithLineView extends LinearLayout {
	private TextView mTitle;
	private View mLine;

	public TabWithLineView(final Context context) {
		super(context);
		init(context, null);
	}

	public TabWithLineView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);

	}

	public TabWithLineView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}


	private void initAttributes(Context context, AttributeSet attributeSet) {
		if(attributeSet == null)
			return;
		TypedArray attr = getTypedArray(context, attributeSet, R.styleable.TabWithLineView);
		if(attr == null) {
			return;
		}
		try {
			String mTitle = attr.getString(R.styleable.TabWithLineView_tab_title);
			setTitle(mTitle);
			boolean isSelect = attr.getBoolean(R.styleable.TabWithLineView_tab_select, false);
			select(isSelect);
		} finally {
			attr.recycle();
		}
	}

	public void select(boolean flag){
		mTitle.setSelected(flag);
		mLine.setSelected(flag);
	}


	public void setTitle(String title){
		mTitle.setText(title);
	}

	protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
		return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
	}

	private void init(Context context, AttributeSet attributeSet) {
		LayoutInflater.from(getContext()).inflate(R.layout.tab_line_view, this);
		mLine = findViewById(R.id.viLine);
		mTitle = (TextView) findViewById(R.id.tvTitle);

		initAttributes(context, attributeSet);
	}
}
