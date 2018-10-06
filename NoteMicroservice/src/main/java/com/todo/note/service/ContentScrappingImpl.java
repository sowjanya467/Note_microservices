package com.todo.note.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ContentScrappingImpl implements ContentScrapping {

	@Override
	public String getTitle(String url) throws IOException {

		Document doc = Jsoup.connect(url).get();
		String title = doc.title();
		return title;
	}

	@Override
	public String getImage(String url) throws IOException {
		return Jsoup.connect(url).get().select("img").first().attr("abs:src");
	}

	@Override
	public String getUrl(String url) throws MalformedURLException {

		return new URL(url).getHost();
	}

}
