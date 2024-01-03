package com.example.recruitment2.Entity;


import com.example.recruitment2.Entity.Enum.EComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "complaint")
public class Complaint {
    @Id
    private String id;
    private String complainedBy;
    private String complainType;
    private String organizationId;
    private String eventId;
    private String postId;
    private String detail;
    private String proof;
    private EComplaintStatus status;
    private LocalDateTime time;
    private LocalDateTime verifiedAt;
    private String verifiedBy;
}