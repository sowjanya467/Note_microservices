package com.todo.note.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.todo.note.dto.Label;
import com.todo.note.dto.NoteDto;
import com.todo.note.dto.UpdateNoteDto;
import com.todo.note.model.NoteModel;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.NoteExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;

/*************************************************************************************************************
 *
 * purpose:interface of note service
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 19-07-18
 *
 **************************************************************************************************/
public interface NoteService {
	public String createNote(NoteDto note, String userId)
			throws LoginExceptionHandling,  ToDoException, IOException;

	void deleteNote(String title, String userId) throws LoginExceptionHandling, ToDoException, NoteExceptionHandling;

	List<NoteModel> viewNotes(String userId) throws ToDoException;

	void updateNote(String userId, String noteId, UpdateNoteDto note) throws LoginExceptionHandling, ToDoException;

	void archiveNote(String noteId) throws ToDoException;

	void unarchiveNote(String noteId) throws ToDoException;

	public void setPin(String noteId) throws ToDoException;

	public void unPin(String noteId) throws ToDoException;

	public void trashNote(String noteId) throws ToDoException;

	public void restoreTrashedNote(String noteId) throws ToDoException;

	public List<NoteModel> viewTrashNotes(String userId) throws ToDoException;

	public List<NoteModel> viewArchivedNotes(String userId) throws ToDoException;

	//public ViewNoteDto viewNote(String noteId, String userId) throws ToDoException;

	public void remainder(String userId, String noteId, String reminderTime) throws ToDoException, ParseException;

	void createNewLabel(String labelName, String userId, String noteId) throws ToDoException;


	public void addLabel(String labelId, String userId, String noteId) throws ToDoException;

	public void deleteLabel(String labelId, String userId) throws ToDoException;

	void updateLabel(String labelId, String userId, String newLabelName) throws ToDoException;

	List<Label> viewLabels(String userId) throws ToDoException;

	void createLabel(String labelName, String userId) throws ToDoException;

	List<NoteModel> sortByTitle(String UserId);

	List<NoteModel> sortByNoteId(String userId);


	//String test(String bucketName);


	void addImage(String noteId, MultipartFile imagePath, String userId) throws ToDoException, AmazonServiceException, SdkClientException, IOException;


}
