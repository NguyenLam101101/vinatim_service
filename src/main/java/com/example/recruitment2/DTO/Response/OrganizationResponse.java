package com.example.recruitment2.DTO.Response;

import com.example.recruitment2.DTO.EventInfo;
import com.example.recruitment2.Entity.Enum.EOrganizationStatus;
import com.example.recruitment2.Entity.SubEntity.OrganizationMember;
import com.example.recruitment2.Entity.SubEntity.Review;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrganizationResponse {
    private String id;
    private String name;
    private String avatar;
    private String banner;
    private EOrganizationStatus status;
    private LocalDateTime approvedAt;
    private String description;
    private String email;
    private String phone;
    private AddressResponse address;
    private String website;
    private int rate;
    private List<Review> reviews;
    private List<String> followers;
    private List<OrganizationMember> members;
    private List<EventInfo> events;
}
