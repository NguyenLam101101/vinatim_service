package com.example.recruitment2.DTO.Request;

import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Entity.Enum.EUnit;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InTransactionRequest {
    private String id;
    private String transactionId;
    private String eventId;
    private EDonateType donateType;
    private Long amount;
    private EUnit unit;
    private String describe;
    private String message;
    private Boolean isAnonymous;
    private ETransactionStatus status;
    private String proof;
    private MultipartFile proofFile;
}
