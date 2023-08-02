package com.knucapstone.rudoori.controller;

import com.knucapstone.rudoori.model.dto.ChatRooms.Chat;
import com.knucapstone.rudoori.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public Chat.Room createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<Chat.Room> findAllRoom() {
        return chatService.findAllRoom();
    }
}
