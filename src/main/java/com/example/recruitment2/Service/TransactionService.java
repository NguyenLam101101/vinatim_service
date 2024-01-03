package com.example.recruitment2.Service;

import com.example.recruitment2.DTO.EStatusCode;
import com.example.recruitment2.DTO.Request.InTransactionRequest;
import com.example.recruitment2.DTO.Request.OutTransactionRequest;
import com.example.recruitment2.DTO.Response.BaseResponse;
import com.example.recruitment2.DTO.Response.InTransactionResponse;
import com.example.recruitment2.DTO.Response.OutTransactionResponse;
import com.example.recruitment2.DTO.UserInfo;
import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Entity.Enum.EUnit;
import com.example.recruitment2.Entity.Event;
import com.example.recruitment2.Entity.InTransaction;
import com.example.recruitment2.Entity.OutTransaction;
import com.example.recruitment2.Entity.SubEntity.EventMember;
import com.example.recruitment2.Entity.User;
import com.example.recruitment2.Repository.EventRepository;
import com.example.recruitment2.Repository.InTransactionRepository;
import com.example.recruitment2.Repository.OutTransactionRepository;
import com.example.recruitment2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CommonService commonService;
    private final InTransactionRepository inTransactionRepository;
    private final OutTransactionRepository outTransactionRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    public BaseResponse<String> createInTransaction(InTransactionRequest request) throws IOException {
        BaseResponse<String> response = new BaseResponse<>(0, "", null);
        InTransaction transaction = InTransaction.builder()
                .transactionId(request.getTransactionId())
                .eventId(request.getEventId())
                .donatedBy(commonService.getCurrentUserId())
                .donateType(request.getDonateType())
                .amount(request.getAmount())
                .unit(EDonateType.MONEY.equals(request.getDonateType()) ? EUnit.VND : request.getUnit())
                .describe(request.getDescribe())
                .message(request.getMessage())
                .time(LocalDateTime.now())
                .isAnonymous(request.getIsAnonymous())
                .status(ETransactionStatus.PENDING)
                .build();
        if(request.getProofFile() != null){
            String proof = commonService.uploadFile(request.getProofFile(), "proof");
            transaction.setProof(proof);
        }
        inTransactionRepository.save(transaction);
        return response;
    }

    public BaseResponse<String> createOutTransaction(OutTransactionRequest request) throws IOException {
        BaseResponse<String> response = new BaseResponse<>(0, "", null);
        OutTransaction transaction = OutTransaction.builder()
                .implementedBy(commonService.getCurrentUserId())
                .eventId(request.getEventId())
                .donateType(request.getDonateType())
                .amount(request.getAmount())
                .unit(request.getUnit())
                .describe(request.getDescribe())
                .detail(request.getDetail())
                .time(LocalDateTime.now())
                .proof(request.getProof())
                .build();
        if(request.getProofFile() != null){
            String proof = commonService.uploadFile(request.getProofFile(), "proof");
            transaction.setProof(proof);
        }
        outTransactionRepository.save(transaction);
        return response;
    }

    public BaseResponse<Map<String, Object>> getInTransactionByEventIdAndStatus(String eventId, ETransactionStatus status, int page, int size){
        BaseResponse<Map<String, Object>> response = new BaseResponse<>(0, "", null);
        Map<String, Object> map = new HashMap<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<InTransaction> transactions = inTransactionRepository.findByEventIdAndStatus(eventId, status, pageable);
        List<InTransactionResponse> transactionResponses = transactions.stream().map(transaction -> inTransactionToResponse(transaction)).toList();
        map.put("data", transactionResponses);
        map.put("count", inTransactionRepository.countAllByEventIdAndStatus(eventId, status));
        response.setData(map);
        return response;
    }

    public BaseResponse<Map<String, Object>> getInTransactionByEventId(String eventId, int page, int size){
        BaseResponse<Map<String, Object>> response = new BaseResponse<>(0, "", null);
        Map<String, Object> map = new HashMap<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<InTransaction> transactions = inTransactionRepository.findByEventId(eventId, pageable);
        List<InTransactionResponse> transactionResponses = transactions.stream().map(transaction -> inTransactionToResponse(transaction)).toList();
        map.put("data", transactionResponses);
        map.put("count", inTransactionRepository.countAllByEventId(eventId));
        response.setData(map);
        return response;
    }

    public BaseResponse<Map<String, Object>> getOutTransactionByEventId(String eventId, int page, int size){
        BaseResponse<Map<String, Object>> response = new BaseResponse<>(0, "", null);
        Map<String, Object> map = new HashMap<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<OutTransaction> transactions = outTransactionRepository.findByEventId(eventId, pageable);
        List<OutTransactionResponse> transactionResponses = transactions.stream().map(transaction -> outTransactionToResponse(transaction)).toList();
        map.put("data", transactionResponses);
        map.put("count", outTransactionRepository.countAllByEventId(eventId));
        response.setData(map);
        return response;
    }

    public InTransactionResponse inTransactionToResponse(InTransaction transaction){
        InTransactionResponse response = InTransactionResponse.builder()
                .id(transaction.getId())
                .eventId(transaction.getEventId())
                .donateType(transaction.getDonateType())
                .amount(transaction.getAmount())
                .unit(transaction.getUnit())
                .describe(transaction.getDescribe())
                .message(transaction.getMessage())
                .time(transaction.getTime())
                .isAnonymous(transaction.getIsAnonymous())
                .status(transaction.getStatus())
                .proof(transaction.getProof())
                .build();
        User user = userRepository.findById(transaction.getDonatedBy()).orElse(null);
        if(user != null){
            response.setDonatedBy(UserInfo.of(user));
        }
        return response;
    }

    public OutTransactionResponse outTransactionToResponse(OutTransaction transaction){
        OutTransactionResponse response = OutTransactionResponse.builder()
                .id(transaction.getId())
                .eventId(transaction.getEventId())
                .donateType(transaction.getDonateType())
                .amount(transaction.getAmount())
                .unit(transaction.getUnit())
                .describe(transaction.getDescribe())
                .detail(transaction.getDetail())
                .time(transaction.getTime())
                .proof(transaction.getProof())
                .build();

        User user = userRepository.findById(transaction.getImplementedBy()).orElse(null);
        if(user != null){
            response.setImplementedBy(UserInfo.of(user));
        }
        return response;
    }

    public BaseResponse<String> approve(String transactionId, ETransactionStatus status){
        BaseResponse<String> response = new BaseResponse<>(0, "", null);
        InTransaction inTransaction = inTransactionRepository.findById(transactionId).orElse(null);
        if(inTransaction == null){
            response.setCode(EStatusCode.NOT_FOUND_OBJECT.code);
            return response;
        }
        Event event = eventRepository.findById(inTransaction.getEventId()).orElse(null);
        if(event == null){
            response.setCode(EStatusCode.NOT_FOUND_OBJECT.code);
            return response;
        }
        List<String> eventMembers = event.getMembers().stream().map(EventMember::getUserId).toList();
        String currentUserId = commonService.getCurrentUserId();
        if(currentUserId == null || !eventMembers.contains(currentUserId)){
            response.setCode(EStatusCode.FORBIDDEN.code);
            return response;
        }
        inTransaction.setStatus(status);
        inTransactionRepository.save(inTransaction);
        return response;
    }
}
