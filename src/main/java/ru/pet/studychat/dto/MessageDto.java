package ru.pet.studychat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private long id;
    private String sender;
    private String room;
    private String message;
    private long created;
    private String status;
}
