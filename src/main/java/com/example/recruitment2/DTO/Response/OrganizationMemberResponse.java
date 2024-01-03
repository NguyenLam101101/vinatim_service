package com.example.recruitment2.DTO.Response;

import com.example.recruitment2.Entity.Enum.EOrganizationMemberRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrganizationMemberResponse {
    private String userId;
    private String name;
    private String avatar;
    private EOrganizationMemberRole role;
    private LocalDateTime joinedAt;
}
