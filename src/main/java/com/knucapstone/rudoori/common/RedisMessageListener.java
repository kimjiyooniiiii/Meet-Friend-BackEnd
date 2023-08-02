package com.knucapstone.rudoori.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.knucapstone.rudoori.model.dto.ChatRooms.Chat;
import com.knucapstone.rudoori.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RedisMessageListener implements MessageListener {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RedisMessageListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    private ChatRoomService chatService; // 채팅 서비스를 적절히 주입하셔야 합니다.

    private Gson objectMapper; // JSON 파싱을 위한 ObjectMapper를 적절히 주입하셔야 합니다.

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((message, pattern) -> {
            try {
                String topic = new String(message.getChannel());
                String messageBody = new String(message.getBody());
                handleMessage(topic, messageBody);
            } catch (Exception e) {
                // 예외 처리
                e.printStackTrace();
            }
        }, new ChannelTopic("chat"));
        return container;
    }

    private void handleMessage(String topic, String messageBody) throws IOException {
        // Redis에서 전달된 메시지 처리 로직
        // 메시지를 적절히 파싱하고, 채팅방에 해당 메시지를 전달하는 로직을 구현하셔야 합니다.
        // 예를 들어, Redis 메시지를 JSON으로 파싱하여 채팅방 ID와 메시지 내용을 추출한 뒤,
        // 해당 채팅방에 메시지를 전달하는 방식으로 구현할 수 있습니다.
        Chat.Message chatMessage = objectMapper.fromJson(messageBody, Chat.Message.class);
        chatService.sendMessageToChatRoom(chatMessage.getRoomId(), chatMessage.getMessage());
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(message.getChannel());
        String messageBody = new String(message.getBody());

        // WebSocket 클라이언트로 메시지 전송
        messagingTemplate.convertAndSend("/topic/" + topic, messageBody);
    }
}
