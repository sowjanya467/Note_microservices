package com.todo.note.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.todo.note.dto.Label;
/*************************************************************************************************************
*
* purpose:
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/
@Repository
public interface LabelElasticRepository extends ElasticsearchRepository<Label, String> 
{
	List<Label> findAllByUserid(String userId);

	Optional<Label> findByLabelName(String name);

	Label findByLabelId(String id);

	void save(Optional<Label> labelFound);



}
