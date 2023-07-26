package com.knucapstone.rudoori.repository;

import com.knucapstone.rudoori.model.entity.ChatRooms;
import com.knucapstone.rudoori.model.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRooms, Long> {

    @Query("select p from ChatRooms p where p.roomName LIKE %:roomName% or p.introduce LIKE %:introduce% ORDER BY p.createdDt DESC")
    List<ChatRooms> findByKeywords(@Param("roomName")String roomName, @Param("introduce")String introduce);
}
