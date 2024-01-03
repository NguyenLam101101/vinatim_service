package com.example.recruitment2.Controller;

import com.example.recruitment2.DTO.Request.EventRequest;
import com.example.recruitment2.Service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<Object> createEvent(EventRequest request) throws IOException {
        return ResponseEntity.ok(eventService.createEvent(request));
    }
}
