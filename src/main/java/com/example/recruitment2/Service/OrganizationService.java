package com.example.recruitment2.Service;

import com.example.recruitment2.DTO.EStatusCode;
import com.example.recruitment2.DTO.EventInfo;
import com.example.recruitment2.DTO.OrganizationInfo;
import com.example.recruitment2.DTO.Request.OrganizationFilterRequest;
import com.example.recruitment2.DTO.Request.OrganizationRequest;
import com.example.recruitment2.DTO.Response.BaseResponse;
import com.example.recruitment2.DTO.Response.OrganizationMemberResponse;
import com.example.recruitment2.DTO.Response.OrganizationResponse;
import com.example.recruitment2.Entity.Area;
import com.example.recruitment2.Entity.Enum.EOrganizationStatus;
import com.example.recruitment2.Entity.Event;
import com.example.recruitment2.Entity.Organization;
import com.example.recruitment2.Entity.SubEntity.Address;
import com.example.recruitment2.Entity.SubEntity.OrganizationMember;
import com.example.recruitment2.Entity.SubEntity.Review;
import com.example.recruitment2.Entity.User;
import com.example.recruitment2.Repository.AreaRepository;
import com.example.recruitment2.Repository.EventRepository;
import com.example.recruitment2.Repository.OrganizationRepository;
import com.example.recruitment2.Repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final CommonService commonService;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BaseResponse<String> createOrganization(OrganizationRequest request) throws IOException {
        BaseResponse<String> response = new BaseResponse<>(0, "", "");
        User currentUser = commonService.getCurrentUser();
        if(currentUser == null){
            response.setCode(EStatusCode.UNAUTHENTICATED.code);
            return response;
        }
        Organization organization = Organization.builder()
                .name(request.getName())
                .description(request.getDescription())
                .email(request.getEmail())
                .phone(request.getPhone())
                .website(request.getWebsite())
                .address(objectMapper.readValue(request.getAddress(), Address.class))
                .createdAt(LocalDateTime.now())
                .createdBy(currentUser.getId())
                .members(objectMapper.readValue(request.getMembers(), new TypeReference<List<OrganizationMember>>() {}))
                .status(EOrganizationStatus.PENDING)
                .build();
        if(request.getAvatarFile() != null) {
            String avatar = commonService.uploadFile(request.getAvatarFile(), "avatar");
            organization.setAvatar(avatar);
        }
        if(request.getBannerFile() != null) {
            String banner = commonService.uploadFile(request.getBannerFile(), "banner");
            organization.setBanner(banner);
        }
        if(organization.getMembers() != null){
            organization.getMembers().forEach(member -> member.setJoinedAt(LocalDateTime.now()));
        }
        Organization savedOrganization = organizationRepository.save(organization);
        List<User> members = new ArrayList<>();
        savedOrganization.getMembers().stream().forEach(member -> {
            User user = userRepository.findById(member.getUserId()).orElse(null);
            if(user != null){
                if(user.getMemberOf() == null){
                    user.setMemberOf(new ArrayList<>());
                }
                user.getMemberOf().add(savedOrganization.getId());
                members.add(user);
            }
        });
        userRepository.saveAll(members);
        return response;
    }

    public BaseResponse<OrganizationResponse> getOrganization(String id){
        BaseResponse<OrganizationResponse> response = new BaseResponse<>(0, "", null);
        Organization organization = organizationRepository.findById(id).orElse(null);
        if(organization == null){
            response.setCode(EStatusCode.NOT_FOUND_OBJECT.code);
            return response;
        }
        OrganizationResponse organizationResponse = organizationToResponse(organization);
        response.setData(organizationResponse);
        return response;
    }

    public OrganizationResponse organizationToResponse(Organization organization){
        OrganizationResponse organizationResponse = OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .avatar(organization.getAvatar())
                .banner(organization.getBanner())
                .description(organization.getDescription())
                .email(organization.getEmail())
                .phone(organization.getPhone())
                .website(organization.getWebsite())
                .members(organization.getMembers())
                .address(organization.getAddress() != null ? commonService.addressToResponse(organization.getAddress()) : null)
                .status(organization.getStatus())
                .reviews(organization.getReviews())
                .rate(
                        (organization.getReviews() != null && organization.getReviews().size() > 0) ?
                                organization.getReviews().stream()
                                        .map(review -> review.getRate())
                                        .reduce(0, (rate, sum) -> sum + rate)/organization.getReviews().size()
                                : 0
                )
                .followers(organization.getFollowers())
                .build();
        //event
        List<Event> events = eventRepository.findEventsByOrganizationId(organization.getId());
        List<EventInfo> eventInfos = events.stream().map(event -> EventInfo.of(event)).toList();
        organizationResponse.setEvents(eventInfos);
        return organizationResponse;
    }

    public BaseResponse<List<OrganizationMemberResponse>> getMembers(String organizationId){
        BaseResponse<List<OrganizationMemberResponse>> response = new BaseResponse<>(0, "", null);
        Organization organization = organizationRepository.findById(organizationId).orElse(null);
        if(organization == null){
            response.setCode(EStatusCode.NOT_FOUND_OBJECT.code);
            response.setMessage("not found organization");
            return response;
        }
        User currentUser = commonService.getCurrentUser();
        Boolean isMember = organization.getMembers().stream().map(OrganizationMember::getUserId).toList().contains(currentUser.getId());
        if(!isMember){
            response.setCode(EStatusCode.FORBIDDEN.code);
            return response;
        }
        List<OrganizationMemberResponse> memberResponses = new ArrayList<>();
        for (OrganizationMember member :organization.getMembers()) {
            User user = userRepository.findById(member.getUserId()).orElse(null);
            if(user != null) {
                memberResponses.add(
                        OrganizationMemberResponse.builder()
                                .userId(user.getId())
                                .role(member.getRole())
                                .joinedAt(member.getJoinedAt())
                                .avatar(user.getAvatar())
                                .name(user.getName())
                                .build()
                );
            }
        };
        response.setData(memberResponses);
        return response;
    }

    public BaseResponse<List<OrganizationInfo>> findOrganizations(OrganizationFilterRequest request){
        BaseResponse<List<OrganizationInfo>> response = new BaseResponse<>(0, "", null);
        Query query = new Query();
        if(request.getProvince() != null && !request.getProvince().isBlank()){
            List<Area> areas = areaRepository.findAreasByProvince(request.getProvince());
            List<String> areaIds = areas.stream().map(Area::getId).toList();
            query.addCriteria(Criteria.where("address.areaId").in(areaIds));
        }
        if(request.getName() != null && !request.getName().isBlank()){
            query.addCriteria(Criteria.where("name").regex(request.getName().trim(), "i"));
        }
        query.addCriteria(Criteria.where("status").in(List.of(EOrganizationStatus.ACCEPTED, EOrganizationStatus.WARNING)));
        query.with(PageRequest.of(request.getPage(), request.getSize()));
        List<Organization> organizations = mongoTemplate.find(query, Organization.class);
        List<OrganizationInfo> organizationInfos = organizations.stream().map(organization -> OrganizationInfo.of(organization)).toList();
        response.setData(organizationInfos);
        return response;
    }
}
