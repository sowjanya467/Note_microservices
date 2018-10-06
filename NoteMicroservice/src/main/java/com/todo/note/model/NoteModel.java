package com.todo.note.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todo.note.dto.Label;

//@Document(collection = "notedb")
@Document(indexName = "noteelasticsearch", type = "note")
@JsonIgnoreProperties(value = { "createdAt", "editedAt" }, allowGetters = true)
public class NoteModel {

	@Id
	private String id;

	@NotBlank
	private String userId;
	@NotBlank
	private String title;
	@NotBlank
	private Content content;
	@CreatedDate
	private String createdAt;
	@LastModifiedDate
	private String editedAt;

	private boolean pin;
	private boolean archive;

	private boolean trash;
	@Field("label")
	private List<Label> label;
	private List<String> image;

	

	public String getId() {
		return id;
	}

	public String setId(String id) {
		return this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getEditedAt() {
		return editedAt;
	}

	public void setEditedAt(String editedAt) {
		this.editedAt = editedAt;
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

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public List<Label> getLabel() {
		if (label == null) {
			this.label = new ArrayList<>();
		}
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}
	
	public List<String> getImage() {
		return image;
	}

	public void setImage(List<String> image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "NoteModel [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", createdAt=" + createdAt + ", editedAt=" + editedAt + ", pin=" + pin + ", archive=" + archive
				+ ", trash=" + trash + ", label=" + label + "]";
	}
	

}
