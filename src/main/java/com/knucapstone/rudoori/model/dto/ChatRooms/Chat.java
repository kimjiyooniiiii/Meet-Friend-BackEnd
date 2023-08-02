package com.knucapstone.rudoori.model.dto.ChatRooms;

import com.google.gson.annotations.SerializedName;
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
    }
    @Getter
    @Setter
    public class Message {
        @SerializedName("type")
        private String type;

        @SerializedName("message")
        private MessageContent messageContent;

        @SerializedName("roomId")
        private String roomId;

        // 생성자, Getter, Setter 등 필요한 메서드를 추가할 수 있습니다.

        // MessageContent 클래스는 message 객체 내의 내용을 나타내는 클래스입니다.
        public static class MessageContent {
            @SerializedName("_id")
            private String messageId;

            @SerializedName("text")
            private String text;

            @SerializedName("createdAt")
            private String createdAt;

            @SerializedName("user")
            private User user;

            // 생성자, Getter, Setter 등 필요한 메서드를 추가할 수 있습니다.
        }

        // User 클래스는 user 객체 내의 정보를 나타내는 클래스입니다.
        public static class User {
            @SerializedName("_id")
            private String userId;

            @SerializedName("name")
            private String name;

            @SerializedName("avatar")
            private String avatarUrl;

            // 생성자, Getter, Setter 등 필요한 메서드를 추가할 수 있습니다.
        }
    }
//    @Getter
//    @Setter
//    public static class Message{
//
//        private String _id;
//        private String text;
//        private String createdAt;
//        private User user;
//        private MessageType type; // 메시지 타입
//        private String roomId; // 방번호
//        private String message; // 메시지
//
//        @Setter
//        @Getter
//        public static class User {
//            private String _id;
//            private String name;
//            private String avatar;
//        }
//
//        // 메시지 타입 : 입장, 퇴장, 채팅
////        INIT, ENTER, QUIT, TALK, URL, IMAGE, VIDEO
//        public enum MessageType {
//            IN, EN, QU, TA, UR, IM, VI
//        }
//
//
//    }
}
