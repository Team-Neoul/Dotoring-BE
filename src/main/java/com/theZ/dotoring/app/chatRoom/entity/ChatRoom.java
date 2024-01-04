package com.theZ.dotoring.app.chatRoom.entity;

import com.theZ.dotoring.app.chatRoom.dto.ChatRoomRequestDTO;
import com.theZ.dotoring.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ChatRoom extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoomId", nullable = false)
    private Long id;

    // 해당 roomName 지정을 해줘야 함.
    private String roomName;

    // 마지막으로 받은 문자 업데이트 해줘야 함.
    private String lastReceivedChat;

    private String mentoName;

    private String menteeName;

    public static ChatRoom of(String mentoName, String menteeName){
        return ChatRoom.builder()
                .roomName(UUID.randomUUID().toString())
                .mentoName(mentoName)
                .menteeName(menteeName)
                .build();
    }

    public void updateLastRecievedChat(String lastReceivedChat){
        this.lastReceivedChat = lastReceivedChat;
    }
}