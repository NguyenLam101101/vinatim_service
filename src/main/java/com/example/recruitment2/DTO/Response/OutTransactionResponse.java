package com.example.recruitment2.DTO.Response;

import com.example.recruitment2.DTO.UserInfo;
import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Entity.Enum.EUnit;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class OutTransactionResponse {
    private String id;
    private UserInfo implementedBy;
    private String eventId;
    private EDonateType donateType;
    private Long amount;
    private EUnit unit;
    private String describe;
    private String detail;
    private LocalDateTime time;
    private String proof;
}
