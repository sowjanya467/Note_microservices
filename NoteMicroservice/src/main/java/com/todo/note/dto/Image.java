package com.todo.note.dto;

import java.io.Serializable;

public class Image implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String imageId;
	private String imagePath;
	private String userId;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", imagePath=" + imagePath + ", userId=" + userId + "]";
	}
}
