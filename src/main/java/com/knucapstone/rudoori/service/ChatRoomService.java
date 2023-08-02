package com.knucapstone.rudoori.service;

import com.knucapstone.rudoori.model.dto.ChatRooms.*;
import com.knucapstone.rudoori.model.entity.ChatMessage;
import com.knucapstone.rudoori.model.entity.ChatRoom;
import com.knucapstone.rudoori.model.dto.ChatRooms.RoomWithMessages;
import com.knucapstone.rudoori.model.entity.UserInfo;
import com.knucapstone.rudoori.repository.ChatMessageRepository;
import com.knucapstone.rudoori.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

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
                .createdAt(chatRoom.getCreatedAt())
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
    public MessageResponse sendMessage(MessageRequest request, UserInfo user, String roomId) {

        ChatMessage message = ChatMessage.builder()
                .chatRoomId(roomId)
                .createdBy(user.getUserId())
                .build();

        messageRepository.insert(message);

        MessageResponse response = MessageResponse.builder()

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
    public List<MessageResponse> enteredRoom(String chatRoomId) {

        List<ChatMessage> messageList = messageRepository.findAllByChatRoomId(chatRoomId);

        List<MessageResponse> response = new ArrayList<>();

        for(ChatMessage m : messageList) {
            MessageResponse r = MessageResponse.builder()
                    .chatRoomId(m.getChatRoomId())
                    ._id(m.get_id())
                    .createdBy(m.getCreatedBy())
                    .createdAt(m.getCreatedAt())
                    .build();
            response.add(r);
        }

        return response;
    }
}
