package com.todo.note.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Link implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(hidden=true)
	private String title;
	@ApiModelProperty(hidden=true)
	private String image;
	@ApiModelProperty(hidden=true)
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

}
