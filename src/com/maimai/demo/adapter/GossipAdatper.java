package com.maimai.demo.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.maimai.demo.R;
import com.maimai.demo.bean.GossipInfo;
import com.maimai.demo.util.PrizeAnimUtil;
import com.maimai.demo.util.Utils;
import com.maimai.demo.widget.CommonTextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/6.
 */
public class GossipAdatper extends ImageLoadAdatper {
	private ArrayList<GossipInfo> mGossipInfos = new ArrayList<GossipInfo>();
	private Rect mRect = new Rect();
	private Context mContext;
	private int mBarHeight;

	public GossipAdatper(Context context, final ArrayList<GossipInfo> gossipInfos) {
		mGossipInfos = gossipInfos;
		mLayoutInflater = LayoutInflater.from(context);
		mContext = context;
	}

	@Override
	public int getCount() {
		return mGossipInfos.size();
	}

	@Override
	public Object getItem(final int position) {
		return mGossipInfos.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.gossip_item, null);
			viewHolder.tvContent = (CommonTextView) convertView.findViewById(R.id.tvContent);
			viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.ivImage);
			viewHolder.commentCountText = (CommonTextView) convertView.findViewById(R.id.tvCommentCount);
			viewHolder.prizeCountText = (CommonTextView) convertView.findViewById(R.id.tvPrizeCount);
			viewHolder.prizeImage = (ImageView) convertView.findViewById(R.id.ivPrizeImage);
			viewHolder.prizeLayout = (LinearLayout) convertView.findViewById(R.id.llPrizeLayout);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		GossipInfo gossipInfo = (GossipInfo) getItem(position);
		showPrizeInfo(viewHolder, gossipInfo);

		viewHolder.tvContent.setText(gossipInfo.content);
		if (gossipInfo.imgList.size() > 0) {
			viewHolder.mImageView.setVisibility(View.VISIBLE);
			displayImage(viewHolder.mImageView, gossipInfo.imgList.get(0));
		} else {
			viewHolder.mImageView.setVisibility(View.GONE);
		}

		return convertView;
	}

	private void showPrizeInfo(final ViewHolder viewHolder, final GossipInfo gossipInfo) {
		viewHolder.prizeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (!gossipInfo.isPrize) {
					gossipInfo.isPrize = true;
					viewHolder.prizeImage.setImageResource(R.drawable.icon_act_prize);
					viewHolder.prizeLayout.getGlobalVisibleRect(mRect);

					if(mBarHeight == 0)
						mBarHeight = Utils.getStatusBarHeight(mContext);
					PrizeAnimUtil.makeAnim(mContext).show(mRect.left, mRect.top - mBarHeight);
				} else {
					gossipInfo.isPrize = false;
					viewHolder.prizeImage.setImageResource(R.drawable.icon_act_prize_gray);
				}
				showPrizeInfo(viewHolder, gossipInfo);
			}
		});
	}

	private class ViewHolder {
		CommonTextView tvContent;
		CommonTextView commentCountText;//评论人数
		CommonTextView prizeCountText; //点赞次数
		ImageView mImageView;         //图片
		ImageView prizeImage;         //点赞手指
		LinearLayout prizeLayout;     //点赞布局
	}

}
	