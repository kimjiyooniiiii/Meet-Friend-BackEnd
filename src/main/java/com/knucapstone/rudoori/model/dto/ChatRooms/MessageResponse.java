package com.knucapstone.rudoori.model.dto.ChatRooms;

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
        private String content;
        private String avatar;

}
