package com.example.recruitment2.DTO.Response;

import com.example.recruitment2.DTO.UserInfo;
import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Entity.Enum.EUnit;
import com.example.recruitment2.Entity.InTransaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class InTransactionResponse {
    private String id;
    private String eventId;
    private UserInfo donatedBy;
    private EDonateType donateType;
    private Long amount;
    private EUnit unit;
    private String describe;
    private String message;
    private LocalDateTime time;
    private Boolean isAnonymous;
    private ETransactionStatus status;
    private String proof;
//    private LocalDateTime approvedBy;
//    private LocalDateTime approvedAt;
}
