package com.example.recruitment2.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventMemberResponse {
    private String userId;
    private String name;
    private String avatar;
    private String joinedAt;
}
