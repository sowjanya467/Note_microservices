package com.todo.note.dto;

import com.todo.note.model.Content;

public class UpdateNoteDto {
	private String title;
	private Content content;
	

	

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

}
