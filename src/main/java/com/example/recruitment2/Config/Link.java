package com.example.recruitment2.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Link {
    @Value("${url.web-server}")
    private String webServer;
}
