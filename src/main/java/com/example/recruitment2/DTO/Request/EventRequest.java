package com.example.recruitment2.DTO.Request;

import com.example.recruitment2.Entity.Enum.EDonateType;
import com.example.recruitment2.Entity.Enum.EEventCategory;
import com.example.recruitment2.Entity.Enum.EEventStatus;
import com.example.recruitment2.Entity.SubEntity.Review;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class EventRequest {
    private String id;
    private String name;
    private String organizationId;
    private EEventStatus status;
    private MultipartFile bannerFile;
    private String banner;
    private String slogan;
    private String description;
    private int progress;
    private List<EDonateType> donateTypes;
    private String addresses;
    private List<EEventCategory> categories;
    private LocalDate startDate;
    private LocalDate endDate;
    private int moneyGoal;
    private String nonMoneyGoal;
    private String members;
}
