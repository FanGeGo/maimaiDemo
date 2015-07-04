package com.maimai.demo.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/2.
 */
public class Utils {

	public static void showToast(Context context, String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	//获取手机状态栏高度
	public static int getStatusBarHeight(Context context){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	public static void showKeyboard(Activity ctx) {
		InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	public static boolean hideKeyboard(Activity ctx) {
		InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		View curFocus = ctx.getCurrentFocus();
		return curFocus != null && imm.hideSoftInputFromWindow(curFocus.getWindowToken(), 0);
	}

}
