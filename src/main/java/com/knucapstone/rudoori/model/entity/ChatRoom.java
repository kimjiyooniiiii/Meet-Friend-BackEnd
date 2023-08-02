package com.knucapstone.rudoori.model.entity;

import jakarta.persistence.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chatRoom")
public class ChatRoom {
    @Id
    private String _id;
    private String roomName;
    private String introduce;
    @CreatedDate
    private LocalDateTime createdAt;
    private List<String> participants;
    private int maxParticipants;
    private List<String> blockedMember;
    private boolean isFull;
}
