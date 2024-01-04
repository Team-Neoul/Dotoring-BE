package com.theZ.dotoring.app.chat.entity;

import com.theZ.dotoring.app.chat.dto.ChatMessageRequestDTO;
import com.theZ.dotoring.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ChatMessage extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id", nullable = false)
    private Long id;

    //채팅방 ID
    private String roomName;

    //보내는 사람
    private String senderName;

    //내용
    private String message;

    public static ChatMessage of(ChatMessageRequestDTO requestDTO, String roomName){
        return ChatMessage.builder()
                .message(requestDTO.getMessage())
                .senderName(requestDTO.getSenderName())
                .roomName(roomName)
                .build();
    }

    public static ChatMessage of(String message, String senderName, String roomName){
        return ChatMessage.builder()
                .message(message)
                .senderName(senderName)
                .roomName(roomName)
                .build();
    }


    public void updateSenderName(String username){
        this.senderName = username;
    }

    public void updateRoomName(String roomName){
        this.roomName = roomName;
    }
}