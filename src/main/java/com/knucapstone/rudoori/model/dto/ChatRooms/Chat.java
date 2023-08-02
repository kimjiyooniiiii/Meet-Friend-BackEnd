package com.knucapstone.rudoori.model.dto.ChatRooms;

import com.knucapstone.rudoori.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

public class Chat {

    @Getter
    public static class Room {
        private String roomId;
        private String name;
        private Set<WebSocketSession> sessions = new HashSet<>();

        @Builder
        public Room(String roomId, String name) {
            this.roomId = roomId;
            this.name = name;
        }

        public void handleActions(WebSocketSession session, Chat.Message chatMessage, ChatService chatService) {
            if (chatMessage.getType().equals(Chat.Message.MessageType.ENTER)) {
                sessions.add(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            }
            sendMessage(chatMessage, chatService);
        }

        public <T> void sendMessage(T message, ChatService chatService) {
            sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
        }

        }
    @Getter
    @Setter
    public static class Message{

        public Message() {
        }

        @Builder
        public Message(MessageType type, String roomId, String sender, String receiver, String message ,String timeLog) {
            this.type = type;
            this.roomId = roomId;
            this.sender = sender;
            this.receiver = receiver;
            this.message = message;
            this.timeLog = timeLog;
        }

        // 메시지 타입 : 입장, 퇴장, 채팅

        public enum MessageType {
            INIT, ENTER, QUIT, TALK, URL, IMAGE, VIDEO
        }


        private MessageType type; // 메시지 타입
        private String roomId; // 방번호
        private String sender; // 메시지 보낸사람
        private String receiver;    //메시지 받는사람
        private String message; // 메시지
        private String timeLog;
    }
}
