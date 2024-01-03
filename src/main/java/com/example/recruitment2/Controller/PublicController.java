package com.example.recruitment2.Controller;

import com.example.recruitment2.DTO.Request.EventFilterRequest;
import com.example.recruitment2.DTO.Request.OrganizationFilterRequest;
import com.example.recruitment2.DTO.Request.UserRequest;
import com.example.recruitment2.DTO.Response.BaseResponse;
import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Repository.AreaRepository;
import com.example.recruitment2.Repository.EventRepository;
import com.example.recruitment2.Service.EventService;
import com.example.recruitment2.Service.OrganizationService;
import com.example.recruitment2.Service.TransactionService;
import com.example.recruitment2.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
    private final UserService userService;
    private final AreaRepository areaRepository;
    private final OrganizationService organizationService;
    private final EventService eventService;
    private final TransactionService transactionService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(String username, String password){
        return ResponseEntity.ok(userService.login(username, password));
    }

    @GetMapping("/get-areas")
    public ResponseEntity<Object> getAreas(){
        BaseResponse response = new BaseResponse<>();
        response.setCode(0);
        response.setData(areaRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-user-by-email-or-phone")
    public ResponseEntity<Object> findUserByEmailOrPhone(String text){
        return ResponseEntity.ok(userService.findUsersByEmailOrPhone(text));
    }

    @GetMapping("/get-organization")
    public ResponseEntity<Object> getOrganization(String id){
        return ResponseEntity.ok(organizationService.getOrganization(id));
    }

    @GetMapping("/get-event")
    public ResponseEntity<Object> getEvent(String id){
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @GetMapping("/filter-organization")
    public ResponseEntity<Object> filterOrganizations(OrganizationFilterRequest request){
        return ResponseEntity.ok(organizationService.findOrganizations(request));
    }

    @GetMapping("/filter-event")
    public ResponseEntity<Object> filterEvents(EventFilterRequest request){
        return ResponseEntity.ok(eventService.findEvents(request));
    }

    @GetMapping("/get-accepted-inTransactions")
    public ResponseEntity<Object> getInTransactions(String eventId, int page, int size){
        return ResponseEntity.ok(transactionService.getInTransactionByEventIdAndStatus(eventId, ETransactionStatus.ACCEPTED, page, size));
    }

    @GetMapping("/get-outTransactions")
    public ResponseEntity<Object> getOutTransactions(String eventId, int page, int size){
        return ResponseEntity.ok(transactionService.getOutTransactionByEventId(eventId, page, size));
    }
}
