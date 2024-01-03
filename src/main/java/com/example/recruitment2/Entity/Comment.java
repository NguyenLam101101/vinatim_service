package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EPosterType;
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
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;
    private String postId;
    private EPosterType posterType;
    private String postedBy;
    private List<String> responses;
    private List<String> media;
    private String content;
    private LocalDateTime time;
    private LocalDateTime editedAt;
}
