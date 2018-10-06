package com.todo.note.dto;
/*************************************************************************************************************
*
* purpose:Response dto
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/

public class ResponseDto 
{
	int status;
	String message;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String response) {
		this.message = response;
	}
	
}
