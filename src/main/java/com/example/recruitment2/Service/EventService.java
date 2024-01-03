package com.example.recruitment2.Service;

import com.example.recruitment2.DTO.EStatusCode;
import com.example.recruitment2.DTO.EventInfo;
import com.example.recruitment2.DTO.OrganizationInfo;
import com.example.recruitment2.DTO.Request.EventFilterRequest;
import com.example.recruitment2.DTO.Request.EventRequest;
import com.example.recruitment2.DTO.Request.OrganizationFilterRequest;
import com.example.recruitment2.DTO.Response.BaseResponse;
import com.example.recruitment2.DTO.Response.EventResponse;
import com.example.recruitment2.Entity.Area;
import com.example.recruitment2.Entity.Enum.EEventStatus;
import com.example.recruitment2.Entity.Enum.EOrganizationMemberRole;
import com.example.recruitment2.Entity.Enum.EOrganizationStatus;
import com.example.recruitment2.Entity.Event;
import com.example.recruitment2.Entity.Organization;
import com.example.recruitment2.Entity.SubEntity.Address;
import com.example.recruitment2.Entity.SubEntity.EventMember;
import com.example.recruitment2.Entity.SubEntity.OrganizationMember;
import com.example.recruitment2.Entity.SubEntity.Review;
import com.example.recruitment2.Repository.AreaRepository;
import com.example.recruitment2.Repository.EventRepository;
import com.example.recruitment2.Repository.OrganizationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final CommonService commonService;
    private final EventRepository eventRepository;
    private final OrganizationRepository organizationRepository;
    private final AreaRepository areaRepository;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BaseResponse<String> createEvent(EventRequest request) throws IOException {
        BaseResponse<String> response = new BaseResponse<>(0, "", "");
        String currentUserId = commonService.getCurrentUserId();
        if(currentUserId == null){
            response.setCode(EStatusCode.UNAUTHENTICATED.code);
            return response;
        }
        Organization organization = organizationRepository.findById(request.getOrganizationId()).orElse(null);
        if(organization == null){
            response.setCode(EStatusCode.NOT_FOUND_OBJECT.code);
            return response;
        }
        OrganizationMember organizationMember = organization.getMembers().stream().filter(member -> member.getUserId().equals(currentUserId)).findFirst().orElse(null);
        if(organizationMember == null || organizationMember.getRole().equals(EOrganizationMemberRole.MEMBER)){
            response.setCode(EStatusCode.FORBIDDEN.code);
            return response;
        }
        Event event = Event.builder()
                .name(request.getName())
                .slogan(request.getSlogan())
                .organizationId(request.getOrganizationId())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .categories(request.getCategories())
                .donateTypes(request.getDonateTypes())
                .addresses(objectMapper.readValue(request.getAddresses(), new TypeReference<List<Address>>() {}))
                .members(objectMapper.readValue(request.getMembers(), new TypeReference<List<EventMember>>() {
                }))
                .moneyGoal(request.getMoneyGoal())
                .nonMoneyGoal(request.getNonMoneyGoal())
                .status(EEventStatus.PENDING)
                .createdBy(currentUserId)
                .createdAt(LocalDateTime.now())
                .totalIn(0)
                .totalOut(0)
                .build();
        if(request.getBannerFile() != null){
            String banner = commonService.uploadFile(request.getBannerFile(), "banner");
            event.setBanner(banner);
        }
        if(event.getMembers() != null){
            event.getMembers().forEach(member -> member.setJoinedAt(LocalDateTime.now()));
        }
        eventRepository.save(event);
        return response;
    }

    public BaseResponse<EventResponse> getEvent(String id){
        BaseResponse<EventResponse> response = new BaseResponse<>(0, "", null);
        Event event = eventRepository.findById(id).orElse(null);
        if(event == null){
            response.setCode(EStatusCode.NOT_FOUND_OBJECT.code);
            return response;
        }
        EventResponse eventResponse = eventToResponse(event);
        response.setData(eventResponse);
        return response;
    }

    public EventResponse eventToResponse(Event event){
        EventResponse eventResponse = EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .status(event.getStatus())
                .banner(event.getBanner())
                .slogan(event.getSlogan())
                .description(event.getDescription())
                .totalIn(event.getTotalIn())
                .totalOut(event.getTotalOut())
                .donateTypes(event.getDonateTypes())
                .categories(event.getCategories())
                .addresses(event.getAddresses().stream().map(commonService::addressToResponse).toList())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .members(event.getMembers())
                .moneyGoal(event.getMoneyGoal())
                .nonMoneyGoal(event.getNonMoneyGoal())
                .reviews(event.getReviews())
                .rate(
                        (event.getReviews() != null && event.getReviews().size() > 0) ?
                                event.getReviews().stream()
                                        .map(Review::getRate)
                                        .reduce(0, (rate, sum) -> sum + rate)/event.getReviews().size()
                                : 0
                )
                .interestedBy(event.getInterestedBy())
                .build();
        //org
        organizationRepository.findById(event.getOrganizationId()).ifPresent(
                organization -> {
                    eventResponse.setOrganization(OrganizationInfo.of(organization));
                    eventResponse.setBankAccount(organization.getBankAccount());
                    eventResponse.setBankName(organization.getBankName());
                    eventResponse.setHostName(organization.getHostName());
                });
        return eventResponse;
    }

    public BaseResponse<List<EventInfo>> findEvents(EventFilterRequest request){
        BaseResponse<List<EventInfo>> response = new BaseResponse<>(0, "", null);
        Query query = new Query();
        if(request.getProvince() != null && !request.getProvince().isBlank()){
            List<Area> areas = areaRepository.findAreasByProvince(request.getProvince());
            List<String> areaIds = areas.stream().map(Area::getId).toList();
            query.addCriteria(Criteria.where("addresses.areaId").in(areaIds));
        }
        if(request.getName() != null && !request.getName().isBlank()){
            query.addCriteria(Criteria.where("name").regex(request.getName().trim(), "i"));
        }
        if(request.getCategories() != null && !request.getCategories().isEmpty()){
            query.addCriteria(Criteria.where("categories").in(request.getCategories()));
        }
        if(request.getDonateTypes() != null && !request.getDonateTypes().isEmpty()){
            query.addCriteria(Criteria.where("donateTypes").in(request.getDonateTypes()));
        }
        query.with(PageRequest.of(request.getPage(), request.getSize()));
        List<Event> events = mongoTemplate.find(query, Event.class);
        List<EventInfo> eventInfos = events.stream().map(event -> EventInfo.of(event)).toList();
        response.setData(eventInfos);
        return response;
    }
}
