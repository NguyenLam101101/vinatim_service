package com.example.recruitment2.DTO.Response;

import com.example.recruitment2.DTO.EventInfo;
import com.example.recruitment2.DTO.OrganizationInfo;
import com.example.recruitment2.Entity.Area;
import com.example.recruitment2.Entity.Enum.EUserGender;
import com.example.recruitment2.Entity.Enum.EUserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserResponse {
    private String id;
    private String email;
    private String phone;
    @JsonProperty(value = "VNeId")
    private String VNeId;
    @JsonProperty(value = "DOB")
    private LocalDate DOB;
    private String name;
    private Area area;
    private EUserGender gender;
    private String password;
    private EUserStatus status;
    private String avatar;
    private LocalDateTime createdAt;
    private List<OrganizationInfo> memberOf;
    private List<OrganizationInfo> followerOf;
    private List<EventInfo> interestedIn;
}
