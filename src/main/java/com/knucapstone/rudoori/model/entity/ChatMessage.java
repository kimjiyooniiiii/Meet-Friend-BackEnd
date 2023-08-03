package com.knucapstone.rudoori.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chatMessage")
public class ChatMessage {
    @Id
    private String _id;
    private String chatRoomId;
    private String createdAt;       // 방 생성일
    private String data;

}
