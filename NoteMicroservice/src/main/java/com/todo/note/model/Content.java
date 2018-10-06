package com.todo.note.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Content implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(hidden=true)
	private List<String> contents;
	@ApiModelProperty(hidden=true)

	private List<Link> links;
	public List<String> getContents() {
		return contents;
	}
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
