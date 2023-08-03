package com.knucapstone.rudoori.controller;

import com.knucapstone.rudoori.common.ApiResponse;
import com.knucapstone.rudoori.model.dto.ChatRooms.*;
import com.knucapstone.rudoori.model.entity.UserInfo;
import com.knucapstone.rudoori.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping("/create")
    public ApiResponse<RoomResponse> createRoom(@RequestBody RoomRequest request, @AuthenticationPrincipal UserInfo user) {

        RoomResponse response = chatRoomService.createRoom(request, user);

        return ApiResponse.createSuccess(response);
    }

    // 전체 방 목록 보기
    @GetMapping("/list")
    public ApiResponse<List<RoomResponse>> getRoomList()
    {
        return ApiResponse.createSuccess(chatRoomService.getRoomList());
    }

    // 채팅방 입장전 미리보기
    @GetMapping("/preview")
    public ApiResponse<RoomPreview> chatRoomPreview(@RequestParam("roomId") String roomId) {

        RoomPreview response = chatRoomService.chatRoomPreview(roomId);

        return ApiResponse.createSuccess(response);
    }

    // 메시지 생성
    @PostMapping("/newMessage")
    public ApiResponse<MessageResponse> sendMessage(@RequestParam("roomId") String roomId, @RequestBody MessageRequest request) {
        MessageResponse response = chatRoomService.sendMessage(request, roomId);
        return ApiResponse.createSuccess(response);
    }

    // 채팅방 입장완료
    @GetMapping("/entered")
    public ApiResponse<EnteredRoomResponse> enteredRoom(@RequestParam("roomId") String roomId) {

        EnteredRoomResponse response = chatRoomService.enteredRoom(roomId);

        return ApiResponse.createSuccess(response);
    }

    // 키워드로 채팅방 검색
    @PostMapping("/searchRoom")
    @ResponseBody   //json으로 바꿔줌
    public ApiResponse<List<RoomResponse>> searchRoomByKeyword(@RequestBody SearchRoomRequest request) {

        List<RoomResponse> responseList = chatRoomService.searchRoomByKeyword(request);


        return ApiResponse.createSuccess(responseList);
    }

}
