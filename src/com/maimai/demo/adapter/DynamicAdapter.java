package com.maimai.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.maimai.demo.R;
import com.maimai.demo.bean.CommentInfo;
import com.maimai.demo.bean.DynamicInfo;
import com.maimai.demo.util.PrizeAnimUtil;
import com.maimai.demo.util.Utils;
import com.maimai.demo.widget.CommonTextView;
import com.maimai.demo.widget.NonScrollableGridView;
import com.maimai.demo.widget.emojicon.EmojiconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/3.
 *
 * "实名动态" 适配器
 */
public class DynamicAdapter extends ImageLoadAdatper {
	public static final int VIEW_TYPE_MORE_PIC = 0; //多张图片显示
	public static final int VIEW_TYPE_ONE_PIC = 1;//显示一张图片
	public static final int VIEW_TYPE_NO_PIC = 2;//无图片

	private List<DynamicInfo> mDynamicInfos = new ArrayList<DynamicInfo>();
	private Context mContext;
	private Rect mRect = new Rect();

	private int mBarHeight;
	private OnAddCommentCallback mCommentCallback;

	public DynamicAdapter(Context context, final List<DynamicInfo> dynamicInfos) {
		mDynamicInfos = dynamicInfos;
		mLayoutInflater = LayoutInflater.from(context);
		mContext = context;

	}

	@Override
	public int getCount() {
		return mDynamicInfos.size();
	}

	@Override
	public Object getItem(final int position) {
		return mDynamicInfos.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		BaseViewHolder viewHolder = null;

		if (convertView == null) {
			switch (getItemViewType(position)) {
				case VIEW_TYPE_ONE_PIC:
					convertView = mLayoutInflater.inflate(R.layout.dynamic_item_onepic, null);
					viewHolder = new OnePicViewHolder();
					initBaseViewHolder(convertView, viewHolder);
					((OnePicViewHolder) viewHolder).mImageView = (ImageView) convertView.findViewById(R.id.ivImage);
					convertView.setTag(viewHolder);

					break;

				case VIEW_TYPE_NO_PIC:
					convertView = mLayoutInflater.inflate(R.layout.dynamic_item_nopic, null);
					viewHolder = new BaseViewHolder();
					initBaseViewHolder(convertView, viewHolder);
					convertView.setTag(viewHolder);

					break;

				case VIEW_TYPE_MORE_PIC:
					convertView = mLayoutInflater.inflate(R.layout.dynamic_item_morepic, null);
					viewHolder = new MorePicViewHolder();
					initBaseViewHolder(convertView, viewHolder);
					((MorePicViewHolder) viewHolder).imageGrid = (NonScrollableGridView) convertView.findViewById(R.id.llImageLayout);
					convertView.setTag(viewHolder);

					break;
			}
		} else {
			viewHolder = (BaseViewHolder) convertView.getTag();
		}

		DynamicInfo dynamicInfo = mDynamicInfos.get(position);
		showBaseInfo(position, viewHolder, dynamicInfo);
		switch (getItemViewType(position)) {
			case VIEW_TYPE_ONE_PIC:
				OnePicViewHolder onePicViewHolder = (OnePicViewHolder) viewHolder;
				ImageView imageView = onePicViewHolder.mImageView;
				LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
				layoutParams.width = 500;
				layoutParams.height = 300;
				displayImage(imageView, dynamicInfo.urlList.get(0));

				break;

			case VIEW_TYPE_MORE_PIC:
				MorePicViewHolder morePicViewHolder = (MorePicViewHolder) viewHolder;
				showPictureLayout(morePicViewHolder, dynamicInfo);

				break;
		}


		return convertView;
	}


	private void initBaseViewHolder(final View convertView, final BaseViewHolder viewHolder) {
		viewHolder.prizeTextPerson = (CommonTextView) convertView.findViewById(R.id.tvCommenPerson);
		viewHolder.showAllComments = (CommonTextView) convertView.findViewById(R.id.tvShowMoreComment);
		viewHolder.commentLayout = (LinearLayout) convertView.findViewById(R.id.llCommentLayout);
		viewHolder.prizeLayout = (LinearLayout) convertView.findViewById(R.id.llPrizeLayout);
		viewHolder.addCommentLayout = (LinearLayout) convertView.findViewById(R.id.llAddComment);
		viewHolder.prizeImage = (ImageView) convertView.findViewById(R.id.ivPrizeImage);
		viewHolder.showMoreCotent = (CommonTextView) convertView.findViewById(R.id.tvMoreContent);
		viewHolder.cotentText = (CommonTextView) convertView.findViewById(R.id.tvContent);
		viewHolder.commentCountText = (CommonTextView) convertView.findViewById(R.id.tvCommentCount);
		viewHolder.prizeCountText = (CommonTextView) convertView.findViewById(R.id.tvPrizeCount);
		viewHolder.nameText = (CommonTextView) convertView.findViewById(R.id.tvName);
		viewHolder.commentRootLayout = (RelativeLayout) convertView.findViewById(R.id.llCommentRootLayout);

	}

	private void showBaseInfo(int position, final BaseViewHolder viewHolder, final DynamicInfo dynamicInfo) {
		showSimpleInfo(viewHolder, dynamicInfo);
		showPrizeInfo(viewHolder, dynamicInfo);
		showCommentInfo(position, viewHolder, dynamicInfo);
		showMoreContent(viewHolder, dynamicInfo);
	}


	private class BaseViewHolder {
		CommonTextView prizeTextPerson; //点赞的人
		CommonTextView nameText;       //名字
		CommonTextView showMoreCotent; //查看全文
		CommonTextView cotentText;     //内容
		CommonTextView commentCountText;//评论人数
		CommonTextView prizeCountText; //点赞次数
		CommonTextView showAllComments; //查看全部评论

		RelativeLayout commentRootLayout; //评论布局
		LinearLayout commentLayout;      //评论列表
		LinearLayout prizeLayout;        //点赞布局
		LinearLayout addCommentLayout;        //添加评论

		ImageView prizeImage;          //点赞手指图片


	}

	private class MorePicViewHolder extends BaseViewHolder {
		NonScrollableGridView imageGrid;
	}

	private class OnePicViewHolder extends BaseViewHolder {
		ImageView mImageView;
	}


	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(int position) {
		int urlSize = mDynamicInfos.get(position).urlList.size();
		int type = VIEW_TYPE_MORE_PIC;

		if (urlSize == 0) {
			type = VIEW_TYPE_NO_PIC;

		} else if (urlSize == 1) {
			type = VIEW_TYPE_ONE_PIC;

		} else if (urlSize > 1) {
			type = VIEW_TYPE_MORE_PIC;
		}

		return type;
	}


	/**
	 * 显示基本信息
	 * @param viewHolder
	 * @param dynamicInfo
	 */
	private void showSimpleInfo(final BaseViewHolder viewHolder, final DynamicInfo dynamicInfo) {
		viewHolder.nameText.setText(dynamicInfo.name);
	}

	/**
	 * 内容的收起与显示全部
	 * @param viewHolder
	 * @param dynamicInfo
	 */
	private void showMoreContent(final BaseViewHolder viewHolder, final DynamicInfo dynamicInfo) {
		viewHolder.cotentText.setText(dynamicInfo.content);

		if (dynamicInfo.isExpand) {
			viewHolder.cotentText.setMaxLines(20);
			viewHolder.showMoreCotent.setText("收起");
			viewHolder.showMoreCotent.setVisibility(View.VISIBLE);

		} else {
			if (viewHolder.cotentText.getText().length() >= 110) {
				viewHolder.cotentText.setMaxLines(5);
				viewHolder.showMoreCotent.setVisibility(View.VISIBLE);
				viewHolder.showMoreCotent.setText("全文");
			} else {
				viewHolder.cotentText.setMaxLines(100);
				viewHolder.showMoreCotent.setVisibility(View.GONE);
			}
		}

		viewHolder.showMoreCotent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (dynamicInfo.isExpand) {
					viewHolder.cotentText.setMaxLines(5);
					viewHolder.showMoreCotent.setVisibility(View.VISIBLE);
					dynamicInfo.isExpand = false;

				} else {
					viewHolder.cotentText.setMaxLines(100);
					viewHolder.showMoreCotent.setVisibility(View.VISIBLE);
					dynamicInfo.isExpand = true;
				}

				showMoreContent(viewHolder, dynamicInfo);
			}
		});
	}


	/**
	 * 显示点赞信息
	 *
	 * @param viewHolder
	 */
	private void showPrizeInfo(final BaseViewHolder viewHolder, final DynamicInfo dynamicInfo) {
		viewHolder.prizeTextPerson.setMovementMethod(LinkMovementMethod.getInstance()); //设置超链接
		SpannableStringBuilder sb = new SpannableStringBuilder();

		for (String s : dynamicInfo.prizeList) {
			sb.append(createSpannableString(s)).append("，");
		}
		sb.delete(sb.length() - 1, sb.length()).append("等").append(String.valueOf(dynamicInfo.prizeList.size())).append("人");
		viewHolder.prizeTextPerson.setText(sb);
		viewHolder.prizeCountText.setText(dynamicInfo.prizeList.size() + "");

		viewHolder.prizeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (!dynamicInfo.isPrize) {
					dynamicInfo.prizeList.add(0, "西凉");
					dynamicInfo.isPrize = true;
					viewHolder.prizeImage.setImageResource(R.drawable.icon_act_prize);

					viewHolder.prizeLayout.getGlobalVisibleRect(mRect);

					if(mBarHeight == 0)
						mBarHeight = Utils.getStatusBarHeight(mContext);
					PrizeAnimUtil.makeAnim(mContext).show(mRect.left, mRect.top - mBarHeight);

				} else {
					dynamicInfo.prizeList.remove(0);
					dynamicInfo.isPrize = false;
					viewHolder.prizeImage.setImageResource(R.drawable.icon_act_prize_gray);

				}
				showPrizeInfo(viewHolder, dynamicInfo);
			}
		});
	}

	/**
	 * 显示评论信息
	 *
	 * @param viewHolder
	 */
	private void showCommentInfo(final int position, final BaseViewHolder viewHolder, final DynamicInfo dynamicInfo) {
		int maxShowComments = 4;
		viewHolder.showAllComments.setVisibility(View.GONE);

		viewHolder.commentCountText.setText(dynamicInfo.commentList.size() + "");
		if (dynamicInfo.commentList.size() == 0) {
			viewHolder.commentRootLayout.setVisibility(View.GONE);

			return;
		} else if (dynamicInfo.commentList.size() > maxShowComments) {
			viewHolder.showAllComments.setVisibility(View.VISIBLE);
			viewHolder.showAllComments.setText("查看全部" + dynamicInfo.commentList.size() + "条评论");
			viewHolder.showAllComments.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					Utils.showToast(mContext, "查看" + dynamicInfo.name + "的全部评论");
				}
			});
		} else {
			viewHolder.commentRootLayout.setVisibility(View.VISIBLE);
		}

		viewHolder.commentLayout.removeAllViews();

		for (int i = 0; i < dynamicInfo.commentList.size(); i++) {
			if(i == maxShowComments)
				break;

			final EmojiconTextView textView = new EmojiconTextView(mContext);
			textView.setTextColor(Color.parseColor("#081424"));
			textView.setTextSize(13);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.comment_item_selector));
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
					.WRAP_CONTENT);
			textView.setPadding(0, 5, 0, 5);
			if (i > 0) {
				layoutParams.topMargin = 5;
			}
			textView.setLayoutParams(layoutParams);

			final CommentInfo commentInfo = dynamicInfo.commentList.get(i);
			SpannableStringBuilder sb = new SpannableStringBuilder();
			sb.append(createSpannableString(commentInfo.getAuthor()));
			if (!TextUtils.isEmpty(commentInfo.getCommenter())) {
				sb.append("回复").append(createSpannableString(commentInfo.getCommenter()));
			}
			sb.append("：").append(commentInfo.getContent());
			textView.setText(sb);
			textView.setTag(commentInfo);
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					if (mCommentCallback != null) {
						String name = commentInfo.getAuthor();
						textView.getGlobalVisibleRect(mRect);
						mCommentCallback.addComment(position, name, false, mRect.bottom);
					}
				}
			});


			textView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(final View v) {
					dynamicInfo.commentList.remove((CommentInfo) textView.getTag());
					showCommentInfo(position, viewHolder, dynamicInfo);
					return true;
				}
			});
			viewHolder.commentLayout.addView(textView);

			viewHolder.addCommentLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					if(mCommentCallback != null){
						viewHolder.commentLayout.getGlobalVisibleRect(mRect);
						mCommentCallback.addComment(position, dynamicInfo.name, true, mRect.bottom);
					}

				}
			});
		}

	}


	/**
	 * 多图显示
	 * @param viewHolder
	 * @param dynamicInfo
	 */
	private void showPictureLayout(final MorePicViewHolder viewHolder, final DynamicInfo dynamicInfo) {
		ArrayList<String> urlList = dynamicInfo.urlList;
		if (urlList.size() == 4) {
			urlList.add(2, "empty"); //补一张空的图片 形成两排4张的效果
		}
		if (viewHolder.imageGrid.getAdapter() == null) {
			viewHolder.imageGrid.setAdapter(new ImageAdatper(urlList, mContext));
		} else {
			((ImageAdatper) viewHolder.imageGrid.getAdapter()).setImgeList(urlList);
		}

	}

	private class MyClickSpan extends ClickableSpan {
		private String name;

		public MyClickSpan(final String name) {
			this.name = name;
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(Color.parseColor("#5477a7"));
			ds.setUnderlineText(false);
		}

		@Override
		public void onClick(View widget) {
			Utils.showToast(mContext, name);
		}
	}

	private SpannableString createSpannableString(String name) {
		SpannableString spannableString = new SpannableString(name);
		spannableString.setSpan(new MyClickSpan(name), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public void setCommentCallback(final OnAddCommentCallback commentCallback) {
		mCommentCallback = commentCallback;
	}

	public interface OnAddCommentCallback{
		/**
		 *
		 * @param position
		 * @param name 对谁进行评论或者回复
		 * @param isComment ture为评论
		 * @param y 需要滚动到某个位置
		 */
		public void addComment(int position, String name, boolean isComment, int y);
	}

}
