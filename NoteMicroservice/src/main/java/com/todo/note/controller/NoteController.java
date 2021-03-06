package com.todo.note.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.todo.note.dto.Label;
import com.todo.note.dto.NoteDto;
import com.todo.note.dto.ResponseDto;
import com.todo.note.dto.UpdateNoteDto;
import com.todo.note.model.NoteModel;
import com.todo.note.service.NoteService;
import com.todo.note.service.ServiceFeign;
import com.todo.note.utility.Messages;
import com.todo.note.utility.RedisRepository;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.NoteExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;

import io.reactivex.netty.protocol.http.client.HttpRequestHeaders;

/*************************************************************************************************************
 *
 * purpose:Note controller
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	NoteService service;
	@Autowired
	Messages messages;
	@Autowired
	RedisRepository repo;
	@Autowired
	ServiceFeign fservice;

	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	/**
	 * method to create note
	 * 
	 * @param note
	 * @param jwtToken
	 * @return ResponseEntity
	 * @throws LoginExceptionHandling
	 * @throws UserExceptionHandling
	 * @throws ToDoException
	 * @throws IOException
	 */
	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseDto createNote(@RequestBody NoteDto note, HttpServletRequest req)
			throws LoginExceptionHandling, ToDoException, IOException {

		// String userId = (String) req.getAttribute("userId");
		String userId = (String) req.getHeader("userId");

		// String userId=repo.getUserId("userId");
		logger.info("userId", userId);
		System.out.println(userId);
		logger.info(messages.get("216"));
		String noteid = service.createNote(note, userId);
		ResponseDto response = new ResponseDto();
		response.setMessage(messages.get("201"));
		response.setStatus(200);

		return response;
		// return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * method to update note
	 * 
	 * @param jwtToken
	 * @param title
	 * @param content
	 * @param id
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/updatenote{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDto> updateNote(@PathVariable String noteId, @RequestBody UpdateNoteDto note,
			HttpServletRequest req) throws LoginExceptionHandling, ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		logger.info(messages.get("217"));
		String userId = (String) req.getHeader("userId");

		System.out.println(userId);
		service.updateNote(userId, noteId, note);
		ResponseDto response = new ResponseDto();
		response.setMessage(messages.get("202"));
		response.setStatus(200);
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * method to delete note
	 * 
	 * @param title
	 * @param token
	 * @param note
	 * @return response
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 * @throws NoteExceptionHandling
	 */

	@RequestMapping(value = "/deletenote{noteId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseDto> deleteNote(@PathVariable String noteId, HttpServletRequest req)
			throws LoginExceptionHandling, ToDoException, NoteExceptionHandling {
		// String jwtToken=request.getHeader("Authorization");
		// String userId = (String) req.getAttribute("userId");
		String userId = (String) req.getHeader("userId");

		logger.info(messages.get("218"));
		service.deleteNote(noteId, userId);
		ResponseDto response = new ResponseDto();
		response.setMessage(messages.get("203"));
		response.setStatus(200);
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * api to display all the notes
	 * 
	 * @param token
	 * @return list of notes
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/viewnotes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteModel>> showListOfNotes(HttpServletRequest req) throws ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		String userId = (String) req.getAttribute("userId");

		logger.info(messages.get("219"));
		List<NoteModel> note = service.viewNotes(userId);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	/**
	 * api to set pin set the pin
	 * 
	 * @param noteId
	 * @return message
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/setpin{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDto> setPin(@PathVariable String noteId) throws ToDoException {

		logger.info(messages.get("220"));
		service.setPin(noteId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("204"));

		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * archive the note
	 * 
	 * @param noteId
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/setarchive{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDto> setArchive(@PathVariable String noteId) throws ToDoException {
		logger.info(messages.get("221"));
		service.archiveNote(noteId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("205"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose: unpin the note
	 * 
	 * @param noteId
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/unpin{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDto> unPin(@PathVariable String noteId) throws ToDoException {

		logger.info(messages.get("222"));
		service.unPin(noteId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("206"));

		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose:unarchive the note
	 * 
	 * @param noteId
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/unarchive{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDto> unArchive(@PathVariable String noteId) throws ToDoException {
		logger.info("unarchive the note");
		service.unarchiveNote(noteId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("207"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose:trash the note
	 * 
	 * @param id
	 * @return ResponseEntity
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/trashnote{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDto> trashNote(@PathVariable String noteId) throws ToDoException {

		logger.info(messages.get("223"));
		service.trashNote(noteId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("208"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
	}

	/**
	 * display all trashed notes
	 * 
	 * @param request
	 * @return ResponseEntity
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/viewtrashednotes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteModel>> viewTrashedNotes(HttpServletRequest req) throws ToDoException {
		logger.info(messages.get("224"));
		String userId = (String) req.getHeader("userId");
		List<NoteModel> note = service.viewTrashNotes(userId);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	/**
	 * display all archived notes
	 * 
	 * @param request
	 * @return ResponseEntity
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/viewarchivednotes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteModel>> viewArchivedNotes(HttpServletRequest req) throws ToDoException {
		logger.info(messages.get("225"));
		String userId = (String) req.getHeader("userId");

		List<NoteModel> note = service.viewArchivedNotes(userId);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	/*
	 * @RequestMapping(value = "/viewsinglenote", method = RequestMethod.PUT) public
	 * ResponseEntity<ViewNoteDto> viewNote(@PathVariable String id,
	 * HttpServletRequest req) throws ToDoException {
	 * 
	 * // String token=request.getHeader("Authorization"); String userId = (String)
	 * req.getAttribute("userId");
	 * 
	 * ViewNoteDto note = service.viewNote(id, userId);
	 * logger.info(messages.get("226")); ResponseDto response = new ResponseDto();
	 * response.setStatus(200); response.setMessage(messages.get("209")); return new
	 * ResponseEntity<ViewNoteDto>(note, HttpStatus.OK);
	 * 
	 * }
	 */

	/**
	 * restore the trashed note
	 * 
	 * @param id
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/restoretrashed", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDto> restoreTrashed(@PathVariable String id) throws ToDoException {
		service.restoreTrashedNote(id);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("210"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * set reminder
	 * 
	 * @param id
	 * @param token
	 * @param reminderTime
	 * @return ResponseEntity
	 * @throws ToDoException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/reminder", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> setReminder(@RequestParam String id, HttpServletRequest req,
			@RequestParam(value = "reminderTime") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") String reminderTime)
			throws ToDoException, ParseException {
		String userId = (String) req.getHeader("userId");

		System.out.println("ssssssssssssssssssssssssssssssss" + id + userId);
		service.remainder(userId, id, reminderTime);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("211"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * create new label to note
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/createlabelfornote{labelName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> setLabel(@PathVariable String labelName, HttpServletRequest req,
			@RequestParam String noteId) throws ToDoException {
		String userId = (String) req.getHeader("userId");

		service.createNewLabel(labelName, userId, noteId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("212"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * create new label
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/createlabel/{labelName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> createLabel(@PathVariable String labelName, HttpServletRequest req)
			throws ToDoException {
		System.out.println("jhqjshg,jhsdg,jaw");
		String userId = (String) req.getHeader("userId");

		System.out.println(labelName);
		service.createLabel(labelName, userId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("212"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * add label to note
	 * 
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/addlabel", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> addLabel(@RequestParam String labelId, HttpServletRequest req,
			@RequestParam String noteId) throws ToDoException {
		String userId = (String) req.getAttribute("userId");

		service.addLabel(labelId, userId, noteId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("213"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose:delete label to note
	 * 
	 * @param labelId
	 * @param token
	 * @return ResponseEntity
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/deletelabel{labelId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseDto> deleteLabel(@PathVariable String labelId, HttpServletRequest req)
			throws ToDoException {
		String userId = (String) req.getAttribute("userId");

		service.deleteLabel(labelId, userId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("214"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose: update label
	 * 
	 * @param labelId
	 * @param token
	 * @param labelName
	 * @return
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/updatelabel", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> renameLabel(@PathVariable String labelId, HttpServletRequest req,
			@RequestParam String labelName) throws ToDoException {
		String userId = (String) req.getAttribute("userId");
		service.updateLabel(labelId, userId, labelName);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("215"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose:display list of labels
	 * 
	 * @param jwtToken
	 * @return list of labels
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/viewlabels", method = RequestMethod.GET)
	public ResponseEntity<List<Label>> showListOfLabels(HttpServletRequest req) throws ToDoException {

		String userId = (String) req.getAttribute("userId");
		logger.info(messages.get("227"));
		List<Label> note = service.viewLabels(userId);

		return new ResponseEntity<List<Label>>(note, HttpStatus.OK);

	}

	/**
	 * to display notes sorted by title
	 * 
	 * @param req
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/viewsortednotes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteModel>> sortNotesByTitle(HttpServletRequest req) throws ToDoException {

		String userId = (String) req.getAttribute("userId");
		logger.info(messages.get("227"));
		List<NoteModel> note = service.sortByTitle(userId);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	/**
	 * purpose:to display notes sorted by id
	 * 
	 * @param req
	 * @return sorted notes by id
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/viewsortednotesbyid", method = RequestMethod.GET)
	public ResponseEntity<List<NoteModel>> sortNotesById(HttpServletRequest req) throws ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		String userId = (String) req.getAttribute("userId");
		logger.info(messages.get("227"));
		List<NoteModel> note = service.sortByNoteId(userId);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	@RequestMapping(value = "/addimage{noteId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> addimage(@PathVariable String noteId, MultipartFile imagePath,
			HttpServletRequest req) throws ToDoException, AmazonServiceException, SdkClientException, IOException {
		String userId = (String) req.getAttribute("userId");

		service.addImage(noteId, imagePath, userId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("212"));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose:to get user list
	 * 
	 * @return user list
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/getuserlist", method = RequestMethod.GET)
	public ResponseEntity<List<?>> displayUsers() throws ToDoException {

		logger.info(messages.get("227"));
		List<?> users = fservice.getAllUsers();

		return new ResponseEntity<List<?>>(users, HttpStatus.OK);

	}

	/*
	 * @RequestMapping(value = "/feingtest", method = RequestMethod.POST) public
	 * ResponseEntity<String> feingtest(@RequestParam String bucket) throws
	 * ToDoException {
	 * 
	 * logger.info(messages.get("227")); String msg=fservice.CreateBucket(bucket);
	 * 
	 * return new ResponseEntity<String>(msg, HttpStatus.OK);
	 * 
	 * }
	 */
}
