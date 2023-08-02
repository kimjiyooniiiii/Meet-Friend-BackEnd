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
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    private Data data;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String _id;     // ${Date.now()}
        private String content;
        @CreatedDate
        private LocalDateTime createdAt;
        private SendUser user;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SendUser {
        private String _id;     //userId
        private String name;
        private String avatar;
    }

}
