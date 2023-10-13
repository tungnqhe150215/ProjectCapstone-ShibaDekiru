package com.sep490.g49.shibadekiru.dto;

import com.sep490.g49.shibadekiru.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private Long chatId;
    private UserAccount userAccount;
    private List<ChatMessageDto> chatMessageDtoList;
}
