package com.knucapstone.rudoori.repository;

import com.knucapstone.rudoori.model.entity.ChatRoom;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoom> searchByKeyword(String[] keywords);
}
