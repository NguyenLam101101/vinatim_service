package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EAdminPrivileges;
import com.example.recruitment2.Entity.Enum.EAdminStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "admin")
public class Admin {
    @Id
    private String id;
    private String email;
    private String phone;
    private String position;
    private String VneId;
    private String name;
    private String password;
    private String avatar;
    private LocalDateTime createdAt;
    private EAdminPrivileges privileges;
    private EAdminStatus status;
}

