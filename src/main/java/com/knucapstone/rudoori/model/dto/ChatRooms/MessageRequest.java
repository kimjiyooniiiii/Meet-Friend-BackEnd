package com.knucapstone.rudoori.model.dto.ChatRooms;

import com.knucapstone.rudoori.model.entity.ChatMessage;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageRequest {
    private String chatRoom_id;
    private String createdBy;
    private ChatMessage.Data data;

    public static class Data {
        private String _id;     // ${Date.now()}
        private String content;
        private ChatMessage.SendUser user;
    }

    public static class SendUser {
        private String _id;     // userId
        private String name;
        private String avatar;
    }
}
