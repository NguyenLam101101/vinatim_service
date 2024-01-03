package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EPostType;
import com.example.recruitment2.Entity.Enum.EReactionType;
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
@Document(collection = "post")
public class Post {

    @Id
    private String id;
    private String content;
    private String postedBy;
    private String organizationId;
    private LocalDateTime time;
    private List<String> media;
    private List<EReactionType> reactions;
    private String editedBy;
    private LocalDateTime editedAt;
    private List<String> sharedBy;
    private List<String> comments;
    private String hashtags;
    private EPostType postType;
}

