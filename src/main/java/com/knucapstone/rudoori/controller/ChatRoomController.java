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


//    // 방 만들기
//    @PostMapping("/create")
//    public void createRoom(@RequestBody RoomRequest createRequest) {
//        chatRoomService.createRoom(createRequest);
//
//    }
//
//    // 밥 모임 검색
//    @GetMapping("/searchMeal")
//    @ResponseBody   //json으로 바꿔줌
//    public List<SearchRoomResponse> searchMealByKeyword(@RequestParam(required = false) LinkedHashMap<String, String> map){
//
//        List<SearchRoomResponse> roomList = chatRoomService.searchMealByKeyword(map);
//
//        return roomList;
//        // keyword 리스트를 순회하며 db에 일치하는 데이터 찾기
//    }

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

    // 채팅방 입장완료
    @GetMapping("/entered")
    public ApiResponse<List<MessageResponse>> enteredRoom(@RequestParam("roomId") String roomId) {

        List<MessageResponse> response = chatRoomService.enteredRoom(roomId);

        return ApiResponse.createSuccess(response);
    }
}
