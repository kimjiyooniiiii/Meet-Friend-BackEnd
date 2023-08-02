package com.knucapstone.rudoori.repository;

import com.knucapstone.rudoori.model.dto.ChatRooms.RoomWithMessages;
import com.knucapstone.rudoori.model.entity.ChatMessage;
import com.knucapstone.rudoori.model.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {


}