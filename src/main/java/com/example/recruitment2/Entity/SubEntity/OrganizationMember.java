package com.example.recruitment2.Entity.SubEntity;

import com.example.recruitment2.Entity.Enum.EOrganizationMemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrganizationMember {
    private String userId;
    private LocalDateTime joinedAt;
    private EOrganizationMemberRole role;
}
