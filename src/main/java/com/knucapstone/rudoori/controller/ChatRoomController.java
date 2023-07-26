package com.knucapstone.rudoori.controller;

import com.knucapstone.rudoori.model.dto.ChatRooms.CreateRoomRequest;
import com.knucapstone.rudoori.model.dto.ChatRooms.SearchRoomResponse;
import com.knucapstone.rudoori.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 방 만들기
    @PostMapping("/create")
    public void createRoom(@RequestBody CreateRoomRequest createRequest) {
        chatRoomService.createRoom(createRequest);

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
