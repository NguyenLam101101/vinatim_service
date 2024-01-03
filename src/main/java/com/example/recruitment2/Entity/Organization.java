package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EOrganizationStatus;
import com.example.recruitment2.Entity.SubEntity.Address;
import com.example.recruitment2.Entity.SubEntity.OrganizationMember;
import com.example.recruitment2.Entity.SubEntity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "organization")
public class Organization {
    @Id
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime editedAt;
    private String editedBy;
    private String avatar;
    private String banner;
    private EOrganizationStatus status;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private String description;
    private List<Review> reviews;
    private String email;
    private String phone;
    private Address address;
    private String website;
    private List<OrganizationMember> members;
    private List<String> followers;
    private String bankAccount;
    private String bankName;
    private String hostName;
}
