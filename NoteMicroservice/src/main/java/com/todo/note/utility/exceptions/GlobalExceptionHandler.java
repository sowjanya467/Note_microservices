package com.todo.note.utility.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todo.note.dto.ResponseDto;

/*************************************************************************************************************
 *
 * purpose:Global exception handling
 *
 * @author sowjanya467
 * @version 1.0
 * @since 15-05-18
 *
 **************************************************************************************************/
@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * method to handle the exception
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ToDoException.class)
	public ResponseEntity<ResponseDto> handleExceptions(ToDoException exception) {
		ResponseDto response = new ResponseDto();
		response.setMessage(exception.getMessage());
		response.setStatus(400);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * method to handle the exception
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(NoteExceptionHandling.class)
	public ResponseEntity<ResponseDto> noteHandlingException(NoteExceptionHandling exception) {
		ResponseDto response = new ResponseDto();
		response.setMessage(exception.getMessage());
		response.setStatus(400);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * method to handle if any internal exception occurs
	 * 
	 * @param exception
	 * @param request
	 * @param reqId
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> handleException(Exception exception, HttpServletRequest request) {
		logger.error("Error occured for: " + exception.getMessage(), exception);
		ResponseDto response = new ResponseDto();
		response.setMessage("Something went wrong");
		response.setStatus(500);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}