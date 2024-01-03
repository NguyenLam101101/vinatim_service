package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EUserGender;
import com.example.recruitment2.Entity.Enum.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String email;
    private String phone;
    private String VNeId;
    private LocalDate DOB;
    private String name;
    private String areaId;
    private EUserGender gender;
    private String password;
    private EUserStatus status;
    private String avatar;
    private LocalDateTime createdAt;
    private List<String> memberOf;
    private List<String> followerOf;
    private List<String> interestedIn;
}