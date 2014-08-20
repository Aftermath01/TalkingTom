package com.example.talkingtom.helpers;

import java.io.Serializable;

public class Mp3Helper implements Serializable{

	private static final long serialVersionUID = -5278044766833441306L;

	private String mTitle;
	private String mAuthor;
	private String mAlbum;
	private String mDuration;
	private String mFilePath;
	private boolean mIsChecked;
	
	public String getFilePath() {
		return mFilePath;
	}
	
	public void setFilePath(String filePath) {
		this.mFilePath = filePath;
	}
	
	public String getAlbum() {
		return mAlbum;
	}
	public void setAlbum(String album) {
		this.mAlbum = album;
	}
	
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		this.mTitle = title;
	}
	public String getAuthor() {
		return mAuthor;
	}
	public void setAuthor(String author) {
		this.mAuthor = author;
	}
	public String getDuration() {
		return mDuration;
	}
	public void setDuration(String string) {
		this.mDuration = string;
	}
	public boolean isChecked() {
		return mIsChecked;
	}
	public void setChecked(boolean isChecked) {
		this.mIsChecked = isChecked;
	}
	
}
