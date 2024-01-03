package com.example.recruitment2.Entity;

import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Entity.Enum.EUnit;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "inTransaction")
public class InTransaction {
    @Id
    private String id;
    private String transactionId;
    private String eventId;
    private String donatedBy;
    private EDonateType donateType;
    private Long amount;
    private EUnit unit;
    private String describe;
    private String message;
    private LocalDateTime time;
    private Boolean isAnonymous;
    private ETransactionStatus status;
    private String proof;
    private LocalDateTime approvedBy;
    private LocalDateTime approvedAt;
}
