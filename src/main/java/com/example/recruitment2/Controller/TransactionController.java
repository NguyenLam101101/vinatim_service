package com.example.recruitment2.Controller;

import com.example.recruitment2.DTO.Request.InTransactionRequest;
import com.example.recruitment2.DTO.Request.OutTransactionRequest;
import com.example.recruitment2.DTO.Response.BaseResponse;
import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/in/create")
    public ResponseEntity<Object> createInTransaction(InTransactionRequest request) throws IOException {
        return ResponseEntity.ok(transactionService.createInTransaction(request));
    }

    @PostMapping("/out/create")
    public ResponseEntity<Object> createOutTransaction(OutTransactionRequest request) throws IOException{
        return ResponseEntity.ok(transactionService.createOutTransaction(request));
    }

    @GetMapping("/get-inTransactions")
    public ResponseEntity<Object> getInTransactions(String eventId, int page, int size){
        return ResponseEntity.ok(transactionService.getInTransactionByEventId(eventId, page, size));
    }

    @PostMapping("/in/approve")
    public ResponseEntity<Object> approveTransaction(String transactionId, ETransactionStatus status){
        return ResponseEntity.ok(transactionService.approve(transactionId, status));
    }
}
