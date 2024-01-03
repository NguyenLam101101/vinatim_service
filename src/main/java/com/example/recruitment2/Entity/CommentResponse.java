package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EPosterType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "comment_response")
public class CommentResponse {
    @Id
    private String id;
    private String commentId;
    private EPosterType posterType;
    private String postedById;
    private List<String> media;
    private String content;
    private LocalDateTime time;
    private LocalDateTime editedAt;
}