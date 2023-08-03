package com.knucapstone.rudoori.service;

import com.knucapstone.rudoori.model.dto.ChatRooms.*;
import com.knucapstone.rudoori.model.entity.ChatMessage;
import com.knucapstone.rudoori.model.entity.ChatRoom;
import com.knucapstone.rudoori.model.entity.ChatRooms;
import com.knucapstone.rudoori.model.entity.UserInfo;
import com.knucapstone.rudoori.repository.ChatMessageRepository;
import com.knucapstone.rudoori.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository messageRepository;

    // 채팅방 생성
//    public RoomResponse createRoom(RoomRequest request, UserInfo user) {
//
//        List<String> participants = new ArrayList<>();
//        participants.add(user.getUserId());     // 방장아이디 참여자로 등록
//
//        ChatRoom chatRoom = ChatRoom.builder()
//                .participants(participants)
//                .roomName(request.getRoomName())
//                .introduce(request.getIntroduce())
//                .maxParticipants(request.getMaxParticipants())
//                .blockedMember(new ArrayList<>())
//                .isFull(false)
//                .build();
//
//        chatRoomRepository.insert(chatRoom);
//
//        RoomResponse response = RoomResponse.builder()
//                ._id(chatRoom.get_id())
//                .roomName(chatRoom.getRoomName())
//                .createdAt(chatRoom.getCreatedAt())
//                .participants(chatRoom.getParticipants())
//                .blockedMember(chatRoom.getBlockedMember())
//                .maxParticipants(chatRoom.getMaxParticipants())
//                .introduce(chatRoom.getIntroduce())
//                .build();
//
//        return response;
//
//    }

    // 전체 방 목록 보기
//    public List<RoomResponse> getRoomList() {
//
//        List<ChatRoom> rooms = chatRoomRepository.findAll();
//        List<RoomResponse> roomResponses = new ArrayList<>();
//        for (ChatRoom r : rooms) {
//            roomResponses.add(RoomResponse.builder()
//                    .roomName(r.getRoomName())
//                    ._id(r.get_id())
//                    .createdAt(r.getCreatedAt())
//                    .introduce(r.getIntroduce())
//                    .maxParticipants(r.getMaxParticipants())
//                    .participants(r.getParticipants())
//                    .blockedMember(r.getBlockedMember())
//                    .build());
//        }
//        return roomResponses;
//    }

    // 채팅 메시지 생성
    public MessageResponse sendMessage(MessageRequest request, UserInfo user, String roomId) {

        ChatMessage.SendUser sendUser = ChatMessage.SendUser.builder()
                ._id(user.getUserId())
                .name(user.getName())
                .build();

        ChatMessage.Data data = ChatMessage.Data.builder()
                .content(request.getContent())
                ._id(LocalDateTime.now().toString())
                .user(sendUser)
                .build();

        ChatMessage message = ChatMessage.builder()
                .chatRoomId(roomId)
                .createdBy(user.getUserId())
                .data(data)
                .build();

        messageRepository.insert(message);

        MessageResponse response = MessageResponse.builder()
                ._id(message.get_id())
                .chatRoomId(message.getChatRoomId())
                .createdBy(message.getCreatedBy())
                .content(message.getData().getContent())
                .createdAt(message.getCreatedAt())
                .build();

        return response;

    }



    public void sendMessageToChatRoom(String roomId, String message) {
    }

    // 게시글 키워드로 검색
    public List<SearchRoomResponse> searchByKeyword(LinkedHashMap<String, String> map) {
        List<ChatRooms> rooms = new ArrayList<>();
        List<SearchRoomResponse> roomList = new ArrayList<>();

        for(String key : map.keySet()) {
            List<ChatRooms> search = chatRoomRepository.findByKeywords(map.get(key), map.get(key));

            if(search == null || search.isEmpty()) {
                System.out.println("데이터가 없습니다");
                continue;
            }else{

                search.stream().forEach(p -> rooms.add(p));
            }
        }

        for(int i=0; i<rooms.size(); i++) {
            String dt = rooms.get(i).getCreatedDt().toString();
            String formatDt = dt.substring(0,4) + "." + dt.substring(5,7) +"." + dt.substring(8,10);

            SearchRoomResponse roomResponse = SearchRoomResponse.builder()
                    .roomName(rooms.get(i).getRoomName())
                    .introduce(rooms.get(i).getIntroduce())
                    .createdDt(formatDt)
                    .build();

            roomList.add(roomResponse);
        }
        return roomList;
    }
}

