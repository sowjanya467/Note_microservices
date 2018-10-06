package com.todo.note.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;


import io.swagger.annotations.ApiModelProperty;
/*************************************************************************************************************
*
* purpose:Note dto
* 
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
**************************************************************************************************/
public class NoteDto {

	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private boolean pin;
	private boolean archive;
	@ApiModelProperty(hidden=true)
	private List<Label> label;
	public List<Label> getLabel() {
		return label;
	}
  
	public void setLabel(List<Label> label) {
		this.label = label;
	}

	private List<String> image;

	public List<String> getImage() {
		return image;
	}

	public void setImage(List<String> image) {
		this.image = image;
	}


	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public  String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

