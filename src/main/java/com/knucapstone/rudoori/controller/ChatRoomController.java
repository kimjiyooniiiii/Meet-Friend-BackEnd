package com.knucapstone.rudoori.controller;

import com.knucapstone.rudoori.common.ApiResponse;
import com.knucapstone.rudoori.model.dto.ChatRooms.*;
import com.knucapstone.rudoori.model.entity.UserInfo;
import com.knucapstone.rudoori.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;



    // 메시지 생성
    @PostMapping("/newMessage")
    public ApiResponse<MessageResponse> sendMessage(@RequestParam("roomId") String roomId, @RequestBody MessageRequest request, @AuthenticationPrincipal UserInfo user) {
        MessageResponse response = chatRoomService.sendMessage(request, user, roomId);
        return ApiResponse.createSuccess(response);
    }
    // 키워드로 방 검색
    @GetMapping("/searchByKeyword")
    @ResponseBody   //json으로 바꿔줌
    public List<SearchRoomResponse> searchByKeyword(@RequestParam(required = false) LinkedHashMap<String, String> map){

        List<SearchRoomResponse> roomList = chatRoomService.searchByKeyword(map);

        return roomList;
        // keyword 리스트를 순회하며 db에 일치하는 데이터 찾기
    }

}
