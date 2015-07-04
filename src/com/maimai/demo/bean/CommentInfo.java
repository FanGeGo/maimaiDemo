package com.maimai.demo.bean;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/4.
 */
public class CommentInfo {
	private String author;
	private String commenter;
	private String content;

	public CommentInfo(final String author, final String commenter, final String content) {
		this.author = author;
		this.commenter = commenter;
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public String getCommenter() {
		return commenter;
	}

	public String getContent() {
		return content;
	}
}
