package com.maimai.demo.util;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.maimai.demo.R;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/10.
 * 点赞动画
 */
public class PrizeAnimUtil {

	private WindowManager mWdm;
	private View mView;
	private WindowManager.LayoutParams mParams;
	private boolean mIsShow;

	private Handler mHandler = new Handler();

	private PrizeAnimUtil(Context context){
		mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		mView = LayoutInflater.from(context).inflate(R.layout.prize_one_view, null);
		setParams();
	}

	public static PrizeAnimUtil makeAnim(Context context){
		return new PrizeAnimUtil(context);
	}

	private void setParams() {
		mParams = new WindowManager.LayoutParams();
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.windowAnimations = R.style.anim_view;//设置动画
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		mParams.gravity = Gravity.LEFT | Gravity.TOP;
	}

	public void show(int x, int y){
		if(!mIsShow){
			mIsShow = true;

			mParams.x = x;
			mParams.y = y;
			mWdm.addView(mView, mParams);

			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					cancel();
				}
			}, 700);
		}
	}

	public void cancel() {
		mWdm.removeView(mView);
		mIsShow = false;
	}
}
