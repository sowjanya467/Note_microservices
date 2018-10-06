package com.todo.note.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.todo.note.model.NoteModel;
@Repository
public interface NoteElasticRepository extends ElasticsearchRepository<NoteModel, String> {

	List<NoteModel> findByUserId(String userId);

	public Optional<NoteModel> findBytitle(String title);


	void save(Optional<NoteModel> note);



	
}
