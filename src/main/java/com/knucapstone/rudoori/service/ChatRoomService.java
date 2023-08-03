package com.knucapstone.rudoori.service;

import com.knucapstone.rudoori.model.dto.ChatRooms.*;
import com.knucapstone.rudoori.model.entity.ChatMessage;
import com.knucapstone.rudoori.model.entity.ChatRoom;
import com.knucapstone.rudoori.model.entity.UserInfo;
import com.knucapstone.rudoori.repository.ChatMessageRepository;
import com.knucapstone.rudoori.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository messageRepository;

    // 채팅방 생성
    public RoomResponse createRoom(RoomRequest request, UserInfo user) {

        List<String> participants = new ArrayList<>();
        participants.add(user.getUserId());     // 방장아이디 참여자로 등록

        ChatRoom chatRoom = ChatRoom.builder()
                .participants(participants)
                .roomName(request.getRoomName())
                .introduce(request.getIntroduce())
                .maxParticipants(request.getMaxParticipants())
                .blockedMember(new ArrayList<>())
                .isFull(false)
                .build();

        chatRoomRepository.insert(chatRoom);

        RoomResponse response = RoomResponse.builder()
                ._id(chatRoom.get_id())
                .roomName(chatRoom.getRoomName())
                .createdAt(chatRoom.getCreatedAt())
                .participants(chatRoom.getParticipants())
                .blockedMember(chatRoom.getBlockedMember())
                .maxParticipants(chatRoom.getMaxParticipants())
                .introduce(chatRoom.getIntroduce())
                .build();

        return response;

    }

    // 전체 방 목록 보기
    public List<RoomResponse> getRoomList() {

        List<ChatRoom> rooms = chatRoomRepository.findAll();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (ChatRoom r : rooms) {
            roomResponses.add(RoomResponse.builder()
                    .roomName(r.getRoomName())
                    ._id(r.get_id())
                    .createdAt(r.getCreatedAt())
                    .introduce(r.getIntroduce())
                    .maxParticipants(r.getMaxParticipants())
                    .participants(r.getParticipants())
                    .blockedMember(r.getBlockedMember())
                    .build());
        }
        return roomResponses;
    }

    // 채팅 메시지 생성
    public MessageResponse sendMessage(MessageRequest request, String roomId) {

        ChatMessage message = ChatMessage.builder()
                .chatRoomId(roomId)
                .createdAt(chatRoomRepository.findById(roomId).toString())
                .data((String) request.getData())
                .build();

        messageRepository.insert(message);

        MessageResponse response = MessageResponse.builder()
                ._id(message.get_id())
                .chatRoomId(message.getChatRoomId())
                .createdAt(message.getCreatedAt())            // 방 생성일
                .data(message.getData())
                .build();

        return response;

    }

    // 채팅방 입장전 미리보기
    public RoomPreview chatRoomPreview(String roomId) {

        Optional<ChatRoom> room = chatRoomRepository.findById(roomId);

        RoomPreview response = RoomPreview.builder()
                ._id(room.get().get_id())
                .roomName(room.get().getRoomName())
                .introduce(room.get().getIntroduce())
                .maxParticipants(room.get().getMaxParticipants())
                .createdAt(room.get().getCreatedAt())
                .build();

        return response;
    }

    // 채팅방 입장완료
    public EnteredRoomResponse enteredRoom(String chatRoomId) {

        Optional<ChatRoom> room = chatRoomRepository.findById(chatRoomId);
        List<ChatMessage> messageList = messageRepository.findAllByChatRoomId(chatRoomId);

        RoomResponse roomResponse = RoomResponse.builder()              // 방 정보
                ._id(room.get().get_id())
                .roomName(room.get().getRoomName())
                .createdAt(room.get().getCreatedAt())
                .blockedMember(room.get().getBlockedMember())
                .maxParticipants(room.get().getMaxParticipants())
                .introduce(room.get().getIntroduce())
                .participants(room.get().getParticipants())
                .isFull(room.get().isFull())
                .build();

        List<MessageResponse> messageResponses = new ArrayList<>();     // 메시지 목록

        for(ChatMessage m : messageList) {
            MessageResponse r = MessageResponse.builder()
                    .chatRoomId(m.getChatRoomId())
                    ._id(m.get_id())
                    .data(m.getData())
                    .createdAt(m.getCreatedAt())
                    .build();
            messageResponses.add(r);
        }

        EnteredRoomResponse response = EnteredRoomResponse.builder()
                .roomResponse(roomResponse)
                .messageResponse(messageResponses)
                .build();

        return response;
    }

    // 키워드로 채팅방 검색
    public List<RoomResponse> searchRoomByKeyword(SearchRoomRequest request) {
        String[] keywords = request.getKeywordList();
        List<ChatRoom> result = chatRoomRepository.searchByKeyword(keywords);

        List<RoomResponse> responseList = new ArrayList<>();
        for(ChatRoom c : result) {
            RoomResponse response = RoomResponse.builder()
                    ._id(c.get_id())
                    .roomName(c.getRoomName())
                    .isFull(c.isFull())
                    .participants(c.getParticipants())
                    .introduce(c.getIntroduce())
                    .maxParticipants(c.getMaxParticipants())
                    .blockedMember(c.getBlockedMember())
                    .createdAt(c.getCreatedAt())
                    .build();

            responseList.add(response);
        }

        return responseList;
    }

    public void sendMessageToChatRoom(String roomId, String message) {
    }

}
