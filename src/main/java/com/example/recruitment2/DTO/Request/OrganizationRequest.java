package com.example.recruitment2.DTO.Request;

import com.example.recruitment2.Entity.Enum.EOrganizationStatus;
import com.example.recruitment2.Entity.SubEntity.Address;
import com.example.recruitment2.Entity.SubEntity.OrganizationMember;
import com.example.recruitment2.Entity.SubEntity.Review;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrganizationRequest {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime editedAt;
    private String editedBy;
    private MultipartFile avatarFile;
    private MultipartFile bannerFile;
    private String avatar;
    private String banner;
    private String description;
    private String email;
    private String address;
    private String website;
    private String phone;
    private String members;
}
