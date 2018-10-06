package com.todo.note.service;

import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;;

@Repository
public class ElasticService {
	private final String INDEX = "fundoonotes";
	private final String TYPE = "notes";

	private RestHighLevelClient restHighLevelClient;

	private ObjectMapper objectMapper;

	public ElasticService(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.restHighLevelClient = restHighLevelClient;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public void insertnote(Object object) {
		Map dataMap = objectMapper.convertValue(object, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE).source(dataMap);
		try {
			IndexResponse response = restHighLevelClient.index(indexRequest);
		} catch (ElasticsearchException e) {
			e.printStackTrace();
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
	}

}
