package com.todo.note.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.todo.note.utility.exceptions.ToDoException;

public class Utility {

	public Utility() {
		// TODO Auto-generated constructor stub
	}
	public boolean isValidateAllFields(String labelName) throws ToDoException {
		System.out.println("validatinggg util");
		if (!isValidlabelName(labelName)) {
			throw new ToDoException("label name is tooo long");
		}

		return false;
	}

	public static boolean isValidlabelName(String labelName) {
		System.out.println(labelName);
		Pattern userNamePattern = Pattern.compile("^[a-z]{0,10}$");
		System.err.println(labelName);
		Matcher matcher = userNamePattern.matcher(labelName);
		return matcher.matches();

	}
}
