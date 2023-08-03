package com.knucapstone.rudoori.common;

import com.google.gson.Gson;
import com.knucapstone.rudoori.model.dto.ChatRooms.Chat;
import com.knucapstone.rudoori.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

    private final ChatSessionRepository chatSessionRepository;
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap();
    private final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = session.getAttributes().get("id").toString();
        chatSessionRepository.createChat(roomId, session);
        sessions.put(session.getId(), session);
        log.info(roomId);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        chatSessionRepository.removeWebSocketSession(session.getAttributes().get("id").toString(),session);
        sessions.remove(session.getId());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info(payload);
        String TYPE = payload.substring(9, 11);

        switch (TYPE) {
            case "TA" -> {
                Chat.Message data = gson.fromJson(payload, Chat.Message.class);
                log.info(data.getType());
                Set<String> sessionId = chatSessionRepository.getWebSocketSessionsByRoomId(data.getRoomId());
                for (String s : sessionId) {
                    WebSocketSession savedSession = sessions.get(s);
                    if (savedSession != null && savedSession.isOpen()) {
                        savedSession.sendMessage(new TextMessage(gson.toJson(data.getMessageContent())));
                    } else {
                        if (savedSession != null) {
                            chatSessionRepository.removeWebSocketSession(data.getRoomId(), savedSession);
                        }
                        sessions.remove(s);
                    }
                }
            }
//                for (String s : sessionId) {
//                    sessions.sendMessage(new TextMessage(gson.toJson(data.getMessage())));
//                }
            case "SY" -> {
                Gson gson = new Gson();
                Chat.Message data = gson.fromJson(payload, Chat.Message.class);
                log.info(data.getType().toString());
//                for (WebSocketSession s : sessions) {
//                    s.sendMessage(new TextMessage(payload));
//                }
            }
            default -> System.out.println(TYPE);
        }

    }
}
