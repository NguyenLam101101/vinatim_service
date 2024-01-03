package com.example.recruitment2.DTO.Request;

import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.EUnit;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OutTransactionRequest {
    private String id;
    private String eventId;
    private EDonateType donateType;
    private Long amount;
    private EUnit unit;
    private String describe;
    private String detail;
    private String proof;
    private MultipartFile proofFile;
}
