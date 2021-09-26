package com.example.roomCostGenerator.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RoomDetailsRepo extends CrudRepository<RoomDetails, String>{
	
	public List<RoomDetails> getByRoomId(String roomId);

}
