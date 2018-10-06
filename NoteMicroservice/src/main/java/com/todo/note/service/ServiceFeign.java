package com.todo.note.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UserMicroservice", url = "http://localhost:8762")
public interface ServiceFeign {
	@GetMapping(path = "user/viewusers")
	public List<?> getAllUsers();

}
