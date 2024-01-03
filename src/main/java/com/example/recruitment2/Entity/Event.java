package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.EEventCategory;
import com.example.recruitment2.Entity.Enum.EEventStatus;
import com.example.recruitment2.Entity.SubEntity.Address;
import com.example.recruitment2.Entity.SubEntity.EventMember;
import com.example.recruitment2.Entity.SubEntity.Review;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "event")
public class Event {
    @Id
    private String id;
    private String name;
    private String organizationId;
    private LocalDateTime createdAt;
    private String createdBy;
    private EEventStatus status;
    private String banner;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private String slogan;
    private String description;
    private long totalIn;
    private long totalOut;
    private List<EDonateType> donateTypes;
    private List<Address> addresses;
    private List<EEventCategory> categories;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Review> reviews;
    private List<EventMember> members;
    private List<String> posts;
    private List<String> interestedBy;
    private long moneyGoal;
    private String nonMoneyGoal;
}