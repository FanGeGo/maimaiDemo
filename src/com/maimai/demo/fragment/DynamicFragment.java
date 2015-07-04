package com.maimai.demo.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.maimai.demo.R;
import com.maimai.demo.adapter.DynamicAdapter;
import com.maimai.demo.adapter.DynamicAdapter.OnAddCommentCallback;
import com.maimai.demo.adapter.EmojiAdapter;
import com.maimai.demo.bean.CommentInfo;
import com.maimai.demo.bean.DynamicInfo;
import com.maimai.demo.bean.Emojicon;
import com.maimai.demo.config.People;
import com.maimai.demo.util.Utils;
import com.maimai.demo.widget.KeyboardLayout;
import com.maimai.demo.widget.emojicon.EmojiconEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/3.
 * "实名动态" Fragment
 */
public class DynamicFragment extends Fragment implements View.OnClickListener, OnAddCommentCallback, KeyboardLayout.onKybdsChangeListener {
	private static final String MY_NAME = "西凉";

	private LinearLayout mCommentLayout;
	private KeyboardLayout mRootLayout;
	private EmojiconEditText mCommentEdit;

	private List<DynamicInfo> mDynamicInfos;
	private ListView mListView;
	private GridView memojiGrid;
	private View emptyView;
	private Button sendBtn;
	private Button emojiBtn;

	private boolean isCommentLayoutShow;
	private boolean isCommentEmpty = true; //评论是否为空
	private boolean isKeyBoardShow;
	private boolean isEmojishow;

	private DynamicAdapter mAdapter;

	private String mName;
	private int mPosition;

	private Handler mHandler = new Handler();

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dynamic_list_page, container, false);
		mListView = (ListView) view.findViewById(R.id.llMainList);
		mDynamicInfos = createList();
		mAdapter = new DynamicAdapter(getActivity(), mDynamicInfos);
		mAdapter.setCommentCallback(this);
		sendBtn = (Button) view.findViewById(R.id.btnSend);
		sendBtn.setOnClickListener(this);

		emojiBtn = (Button) view.findViewById(R.id.btnEmoji);
		emojiBtn.setOnClickListener(this);

		memojiGrid = (GridView) view.findViewById(R.id.gvEmojiGrid);

		memojiGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				inputEmoji(mCommentEdit, People.DATA[position]);
			}
		});
		mRootLayout = (KeyboardLayout) view.findViewById(R.id.keyboardLayout);

		mRootLayout.setOnkbdStateListener(this);
		emptyView = view.findViewById(R.id.viEmptyView);

		mListView.setAdapter(mAdapter);

		emptyView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(final View v, final MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (isCommentLayoutShow)
						return false;

					hideCommentLayout();
				}
				return true;
			}
		});
		mCommentLayout = (LinearLayout) view.findViewById(R.id.rlCommentLayout);
		mCommentEdit = (EmojiconEditText) view.findViewById(R.id.etEditComment);
		mCommentEdit.setOnClickListener(this);

		mCommentEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

			}

			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
				if (s.length() == 0) {
					isCommentEmpty = true;
					sendBtn.setTextColor(getResources().getColor(R.color.btn_comment_text_gray));
					sendBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_gray_bg));
				} else {
					isCommentEmpty = false;
					sendBtn.setTextColor(getResources().getColor(android.R.color.white));
					sendBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_blue_bg));
				}
			}

			@Override
			public void afterTextChanged(final Editable s) {

			}
		});
		return view;
	}

	private List<DynamicInfo> createList() {
		List<DynamicInfo> list = new ArrayList<DynamicInfo>();
		list.add(createInfo());
		list.add(createInfo2());
		list.add(createInfo3());
		list.add(createInfo4());
		list.add(createInfo());
		list.add(createInfo2());
		list.add(createInfo3());
		list.add(createInfo4());

		return list;
	}

	private DynamicInfo createInfo() {
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.name = "刘德华";
		dynamicInfo.content = "今天在台湾召开的Computex大会上，戴尔同样携多款新品亮相，其中包含两款3000系列的桌面一体机、一台运行Windows 8.1系统Inspiron Micro桌面微型主机、Nextbook Flexx 11/10两款入门二合一设备和全新的 " +
				"Inspiron 5000系列。";

		dynamicInfo.prizeList.add("周杰伦");
		dynamicInfo.prizeList.add("王力宏");

		dynamicInfo.urlList.add("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s240-c/A%252520Photographer.jpg");
		dynamicInfo.urlList.add("https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s240-c/A%252520Song%252520of%252520Ice" +
				"%252520and%252520Fire.jpg");
		dynamicInfo.urlList.add("https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s240-c/Another%252520Rockaway%252520Sunset" +
				".jpg");
		dynamicInfo.urlList.add("https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s240-c/Antelope%252520Butte.jpg");
		dynamicInfo.urlList.add("https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s240-c/Antelope%252520Walls.jpg");

		CommentInfo commentInfo1 = new CommentInfo("小明", "小贝", "我擦");
		CommentInfo commentInfo2 = new CommentInfo("李晨", "范冰冰", "好的，知道了。");

		dynamicInfo.commentList.add(commentInfo1);
		dynamicInfo.commentList.add(commentInfo2);

		return dynamicInfo;
	}

	private DynamicInfo createInfo2() {
		DynamicInfo dynamicInfo = new DynamicInfo();

		dynamicInfo.name = "刘明";
		dynamicInfo.content = "【美国CNN女主播就南海发问 " +
				"崔天凯直面回应】4日，中国驻美大使崔天凯接受CNN知名主播专访，女主播开门见山，直指中美在南海问题上的分歧。崔天凯镇定回应：南海现状已经被其他国家改变很久很久了，我们所做的只是还原（南海）应有的状态。我们在那有军事设施，但那是防御性的。";

		dynamicInfo.prizeList.add("周杰伦");
		dynamicInfo.prizeList.add("王力宏");

		dynamicInfo.urlList.add("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s240-c/A%252520Photographer.jpg");
		dynamicInfo.urlList.add("https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s240-c/A%252520Song%252520of%252520Ice" +
				"%252520and%252520Fire.jpg");
		dynamicInfo.urlList.add("https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s240-c/Another%252520Rockaway%252520Sunset" +
				".jpg");
		dynamicInfo.urlList.add("https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s240-c/Antelope%252520Butte.jpg");
//		dynamicInfo.urlList.add("https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s240-c/Antelope%252520Walls.jpg");

		CommentInfo commentInfo1 = new CommentInfo("李华", "蔡柳叶", "SB");
		CommentInfo commentInfo2 = new CommentInfo("李晨", "范冰冰", "好的，知道了。");
		CommentInfo commentInfo3 = new CommentInfo("蔡柳叶", "李华", "SB");

		dynamicInfo.commentList.add(commentInfo1);
		dynamicInfo.commentList.add(commentInfo2);
		dynamicInfo.commentList.add(commentInfo3);

		return dynamicInfo;
	}


	private DynamicInfo createInfo3() {
		DynamicInfo dynamicInfo = new DynamicInfo();

		dynamicInfo.name = "汤灿";
		dynamicInfo.content = "中国驻美大使崔天凯接受CNN知名主播专访，女主播开门见山，直指中美在南海问题上的分歧。崔天凯镇定回应：南海现状已经被其他国家改变很久很久了，我们所做的只是还原（南海）应有的状态。我们在那有军事设施，但那是防御性的。";

		dynamicInfo.prizeList.add("周杰伦");
		dynamicInfo.prizeList.add("王力宏");

		dynamicInfo.urlList.add("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s240-c/A%252520Photographer.jpg");

		CommentInfo commentInfo1 = new CommentInfo("李华", "蔡柳叶", "SB");
		CommentInfo commentInfo2 = new CommentInfo("李晨", "范冰冰", "好的，知道了。");
		CommentInfo commentInfo3 = new CommentInfo("蔡柳叶", "李华", "SB");

		dynamicInfo.commentList.add(commentInfo1);
		dynamicInfo.commentList.add(commentInfo2);
		dynamicInfo.commentList.add(commentInfo3);

		return dynamicInfo;
	}

	private DynamicInfo createInfo4() {
		DynamicInfo dynamicInfo = new DynamicInfo();

		dynamicInfo.name = "无图";
		dynamicInfo.content = "没有图片。";

		dynamicInfo.prizeList.add("周杰伦");
		dynamicInfo.prizeList.add("王力宏");

		CommentInfo commentInfo1 = new CommentInfo("李华", "蔡柳叶", "SB");
		CommentInfo commentInfo2 = new CommentInfo("李晨", "范冰冰", "好的，知道了。");
		CommentInfo commentInfo3 = new CommentInfo("蔡柳叶", "李华", "SB");

		dynamicInfo.commentList.add(commentInfo1);
		dynamicInfo.commentList.add(commentInfo2);
		dynamicInfo.commentList.add(commentInfo3);

		return dynamicInfo;
	}

	@Override
	public void onClick(final View v) {
		if (v.getId() == R.id.btnSend) {
			if (!isCommentEmpty) {

				CommentInfo commentInfo = new CommentInfo(MY_NAME, mName, mCommentEdit.getText().toString());
				mDynamicInfos.get(mPosition).commentList.add(commentInfo);
				mAdapter.notifyDataSetChanged();
				mCommentEdit.setText("");
				hideCommentLayout();
			}
		} else if (v.getId() == R.id.btnEmoji) {
			showCommentLayout(!isKeyBoardShow);
		} else if (v.getId() == R.id.etEditComment) {
			memojiGrid.setVisibility(View.GONE);
			isKeyBoardShow = true;
		}
	}

	@Override
	public void addComment(final int position, final String name, boolean isComment, final int y) {
		if (name.equals(MY_NAME))
			return;

		mPosition = position;
		mName = name;
		if (isComment) {
			mCommentEdit.setHint("评论" + name + "：");
		} else {
			mCommentEdit.setHint("回复" + name + "：");
		}


		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Rect rect = new Rect();
				mCommentLayout.getGlobalVisibleRect(rect);
				mListView.smoothScrollBy((y - rect.top), 500); //点击回复的那一项滚动到评论框上边
			}
		}, 300);

		showCommentLayout(true);
	}


	@Override
	public void onKeyBoardStateChange(final int state) {
		switch (state) {
			case KeyboardLayout.KEYBOARD_STATE_HIDE:
				isKeyBoardShow = false;
				if (!isEmojishow) { //显示emoji时不隐藏评论布局
					mCommentLayout.setVisibility(View.GONE);
					isCommentLayoutShow = true;
					isEmojishow = false;
				}
				break;
		}
	}

	/**
	 * 隐藏评论布局
	 */
	private void hideCommentLayout() {
		emptyView.setVisibility(View.GONE);
		mCommentLayout.setVisibility(View.GONE);
		Utils.hideKeyboard(getActivity());
		isCommentLayoutShow = true;
		isKeyBoardShow = false;
	}

	/**
	 * 显示评论布局
	 *
	 * @param showKeyboard 是显示键盘还是表情框
	 */
	private void showCommentLayout(boolean showKeyboard) {
		isKeyBoardShow = showKeyboard;
		mCommentLayout.setVisibility(View.VISIBLE);
		emptyView.setVisibility(View.VISIBLE);
		mCommentEdit.requestFocus();
		isCommentLayoutShow = false;
		if (showKeyboard) {
			isEmojishow = false;
			Utils.showKeyboard(getActivity());
			memojiGrid.setVisibility(View.GONE);
			emojiBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.topic_e_bg_selector));
		} else {
			emojiBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.topic_t_bg_selector));
			Utils.hideKeyboard(getActivity());
			isEmojishow = true;

			mHandler.postDelayed(new Runnable() { //加个延时是为了让键盘完全消失
				@Override
				public void run() {
					memojiGrid.setVisibility(View.VISIBLE);
					if (memojiGrid.getAdapter() == null) {
						memojiGrid.setAdapter(new EmojiAdapter(getActivity(), People.DATA));
					}
				}
			}, 50);
		}
	}


	private void inputEmoji(EditText editText, Emojicon emojicon) {
		if (editText == null || emojicon == null) {
			return;
		}
		int start = editText.getSelectionStart();
		int end = editText.getSelectionEnd();
		if (start < 0) {
			editText.append(emojicon.getEmoji());
		} else {
			editText.getText().replace(Math.min(start, end), Math.max(start, end), emojicon.getEmoji(), 0, emojicon.getEmoji().length());
		}
	}
}
