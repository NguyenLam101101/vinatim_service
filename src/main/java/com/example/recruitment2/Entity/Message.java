package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EMessageSender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "message")
public class Message {

    @Id
    private String id;
    private String userId;
    private String organizationId;
    private EMessageSender sender;
    private String message;
    private List<String> media;
    private LocalDateTime time;
}
