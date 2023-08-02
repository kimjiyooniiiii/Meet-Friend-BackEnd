package com.knucapstone.rudoori.service;

import com.knucapstone.rudoori.model.dto.ChatRooms.CreateRoomRequest;
import com.knucapstone.rudoori.model.dto.ChatRooms.SearchRoomResponse;
import com.knucapstone.rudoori.model.entity.ChatRooms;
import com.knucapstone.rudoori.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    // 방 만들기
    public void createRoom(CreateRoomRequest createRequest) {
//        if(createRequest.getRoomName() == null
//        || createRequest.getIntroduce() ==)

        //entity 생성
        ChatRooms room = ChatRooms.builder()
                .roomName(createRequest.getRoomName())
                .category(createRequest.getCategory())
                .hostId(createRequest.getHostId())
                .numbers(createRequest.getNumbers())
                .introduce(createRequest.getIntroduce())
                .isFinished(false)
                .build();

        chatRoomRepository.save(room);
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


    public void sendMessageToChatRoom(String roomId, String message) {
    }
}
