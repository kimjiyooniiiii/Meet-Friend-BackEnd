package com.knucapstone.rudoori.model.dto.ChatRooms;

import com.knucapstone.rudoori.model.entity.ChatMessage;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageResponse {
    private String _id;
    private String chatRoomId;
    private String createdBy;
    private LocalDateTime createdAt;
    private ChatMessage.Data data;

    public static class Data {
        private String _id;     // ${Date.now()}
        private String content;
        private LocalDateTime createdAt;
        private ChatMessage.SendUser user;
    }

    public static class SendUser {
        private String _id;     //userId
        private String name;
        private String avatar;
    }
}
