package com.knucapstone.rudoori.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatRoomId;
    String hostId;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdDt;
    Boolean isFinished;
    String category;
    int numbers;

    @Column(length = 30)
    String roomName;
    @Column(length = 100)
    String introduce;
}
