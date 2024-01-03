package com.example.recruitment2.Entity.SubEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserInfo {
    private String userId;
    private String avatar;
    private String name;
}