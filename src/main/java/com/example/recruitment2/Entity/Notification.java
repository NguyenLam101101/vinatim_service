package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.ENotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notification")
public class Notification {
    @Id
    private String id;
    private ENotificationType type;
    private LocalDateTime time;
    private String receiver;
    private String content;
    private boolean isRead;
    private String objectId;
}
