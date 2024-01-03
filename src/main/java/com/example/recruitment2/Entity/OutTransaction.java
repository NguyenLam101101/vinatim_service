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
@Document(collection = "outTransaction")
public class OutTransaction {
    @Id
    private String id;
    private String implementedBy;
    private String eventId;
    private EDonateType donateType;
    private Long amount;
    private EUnit unit;
    private String describe;
    private String detail;
    private LocalDateTime time;
    private String proof;
}
