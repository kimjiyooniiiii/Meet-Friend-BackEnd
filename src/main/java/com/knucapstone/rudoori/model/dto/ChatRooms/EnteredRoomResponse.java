package com.knucapstone.rudoori.model.dto.ChatRooms;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnteredRoomResponse {

    private RoomResponse roomResponse;
    private List<MessageResponse> messageResponse;

}
