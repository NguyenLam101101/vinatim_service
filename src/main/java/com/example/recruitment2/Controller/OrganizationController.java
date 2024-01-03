package com.example.recruitment2.Controller;

import com.example.recruitment2.DTO.Request.OrganizationRequest;
import com.example.recruitment2.Service.CommonService;
import com.example.recruitment2.Service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrganization(OrganizationRequest request) throws IOException {
        return ResponseEntity.ok(organizationService.createOrganization(request));
    }

    @GetMapping("/members")
    public ResponseEntity<Object> getOrganizationMembers(String id){
        return ResponseEntity.ok(organizationService.getMembers(id));
    }
}
