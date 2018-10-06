package com.todo.note.service;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ContentScrapping {

	public String getTitle(String url) throws IOException;

	public String getImage(String url) throws IOException;

	public String getUrl(String url) throws MalformedURLException;

}
