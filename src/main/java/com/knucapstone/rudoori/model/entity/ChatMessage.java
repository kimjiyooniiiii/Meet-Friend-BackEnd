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
    @Indexed
    private String chatRoomId;
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    private Data data;

    public static class Data {
        private String _id;     // ${Date.now()}
        private String content;
        private LocalDateTime createdAt;
        private SendUser user;
    }

    public static class SendUser {
        private String _id;     //userId
        private String name;
        private String avatar;
    }

}
