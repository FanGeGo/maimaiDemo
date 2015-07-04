package com.maimai.demo.bean;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/3.
 */
public class DynamicInfo {
	public String name;
	public String content;

	public boolean isPrize;//是否点赞
	public boolean isExpand;//是否展开全文

	public ArrayList<String> prizeList = new ArrayList<String>(); //点赞人名
	public ArrayList<String> urlList = new ArrayList<String>(); //图片列表
	public ArrayList<CommentInfo> commentList = new ArrayList<CommentInfo>(); //评论列表

}
