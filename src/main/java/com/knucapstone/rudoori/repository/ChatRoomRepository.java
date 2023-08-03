package com.knucapstone.rudoori.repository;

import com.knucapstone.rudoori.model.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String>, ChatRoomRepositoryCustom {

}
