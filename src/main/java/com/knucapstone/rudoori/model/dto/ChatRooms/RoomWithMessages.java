package com.knucapstone.rudoori.model.dto.ChatRooms;

import com.knucapstone.rudoori.model.entity.ChatMessage;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomWithMessages {
    @Id
    String getId();

    String getRoomName();

    String getIntroduce();

    LocalDateTime getCreatedAt();

    List<String> getParticipants();

    int getMaxParticipants();

    List<String> getBlockedMember();

    boolean isFull();

    List<ChatMessage> getChatMessages();
}
