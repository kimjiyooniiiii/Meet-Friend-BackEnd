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
    private String createdAt;      // 방 생성일
    private String data;

}
