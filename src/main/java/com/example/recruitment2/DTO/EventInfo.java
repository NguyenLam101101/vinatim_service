package com.example.recruitment2.DTO;

import com.example.recruitment2.Entity.Enum.EEventStatus;
import com.example.recruitment2.Entity.Event;
import com.example.recruitment2.Entity.Organization;
import com.example.recruitment2.Entity.SubEntity.Review;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventInfo {
    private String id;
    private String name;
    private EEventStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rate;
    private int interestedCount;
    private String banner;
    private String slogan;

    public static EventInfo of(Event event){
        EventInfo eventInfo = new EventInfo();
        eventInfo.setId(event.getId());
        eventInfo.setName(event.getName());
        eventInfo.setStatus(event.getStatus());
        eventInfo.setStartDate(event.getStartDate());
        eventInfo.setEndDate(event.getEndDate());
        eventInfo.setBanner(event.getBanner());
        eventInfo.setSlogan(eventInfo.getSlogan());
        eventInfo.setRate(
            (event.getReviews() != null && event.getReviews().size() > 0) ?
                event.getReviews().stream()
                        .map(Review::getRate)
                        .reduce(0, (rate, sum) -> sum + rate)/event.getReviews().size()
            : 0
        );
        eventInfo.setInterestedCount(event.getInterestedBy() != null ? event.getInterestedBy().size() : 0);
        return eventInfo;
    }
}
