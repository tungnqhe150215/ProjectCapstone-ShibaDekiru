package com.sep490.g49.shibadekiru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private Long chatMessageId;
    private String messageContent;
    private LocalDateTime time;
    private Boolean isDeleted;
    private ChatDto chatDto;

}
