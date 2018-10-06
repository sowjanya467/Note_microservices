package com.todo.note.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.todo.note.model.NoteModel;
@Repository
public interface NoteAwselasticrepo extends ElasticsearchRepository<NoteModel, String> {

	
}
