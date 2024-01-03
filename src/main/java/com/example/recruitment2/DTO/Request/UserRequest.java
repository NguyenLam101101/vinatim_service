package com.example.recruitment2.DTO.Request;

import com.example.recruitment2.Entity.Enum.EUserGender;
import com.example.recruitment2.Entity.Enum.EUserStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String id;
    private String name;
    private String VNeId;
    private String phone;
    private String email;
    private String password;
    private String avatar;
    private MultipartFile avatarFile;
    private String areaId;
    private EUserGender gender;
    private LocalDate DOB;
    private EUserStatus status;
}
