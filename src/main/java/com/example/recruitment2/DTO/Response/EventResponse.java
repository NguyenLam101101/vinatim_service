package com.example.recruitment2.DTO.Response;

import com.example.recruitment2.DTO.OrganizationInfo;
import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.EEventCategory;
import com.example.recruitment2.Entity.Enum.EEventStatus;
import com.example.recruitment2.Entity.SubEntity.Address;
import com.example.recruitment2.Entity.SubEntity.EventMember;
import com.example.recruitment2.Entity.SubEntity.Review;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class EventResponse {
    private String id;
    private String name;
    private OrganizationInfo organization;
    private EEventStatus status;
    private String banner;
    private String slogan;
    private String description;
    private long totalIn;
    private long totalOut;
    private List<EDonateType> donateTypes;
    private List<AddressResponse> addresses;
    private List<EEventCategory> categories;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<EventMember> members;
    private int rate;
    private List<Review> reviews;
    private List<String> interestedBy;
    private long moneyGoal;
    private String nonMoneyGoal;
    private String bankAccount;
    private String bankName;
    private String hostName;
    private int donateCount;
}
