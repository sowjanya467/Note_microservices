package com.todo.note.utility;

@SuppressWarnings("hiding")
public interface RedisRepository {

	public void setToken(String jwtToken);

	public String getUserId(String userId);
	//public String setUserId(String userId);

	String getToken(String userId);

	String setUserId(String userId);

	//public String getUserId(String userId);

}
