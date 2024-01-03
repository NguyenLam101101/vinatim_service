package com.example.recruitment2.Service;

import com.example.recruitment2.DTO.Response.*;
import com.example.recruitment2.DTO.EventInfo;
import com.example.recruitment2.DTO.OrganizationInfo;
import com.example.recruitment2.Entity.Area;
import com.example.recruitment2.Entity.Event;
import com.example.recruitment2.Entity.Organization;
import com.example.recruitment2.Entity.SubEntity.Address;
import com.example.recruitment2.Entity.SubEntity.Review;
import com.example.recruitment2.Entity.User;
import com.example.recruitment2.Repository.AreaRepository;
import com.example.recruitment2.Repository.EventRepository;
import com.example.recruitment2.Repository.OrganizationRepository;
import com.example.recruitment2.Repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommonService {
    @Value("${google.cloud.project-id}")
    private String googleProjectId;
    @Value("${google.cloud.bucket-name}")
    private String googleBucketName;
    @Value("${google.cloud.oath.path}")
    private String googleKeyFile;

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final OrganizationRepository organizationRepository;
    private final EventRepository eventRepository;

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.
                fromStream(new FileInputStream(googleKeyFile))
                .createScoped("https://www.googleapis.com/auth/cloud-platform");

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(googleProjectId)
                .build()
                .getService();
        String objectName = folderName+ "/" + UUID.randomUUID() + "---" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(googleBucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setAcl(List.of(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                .build();
        Blob uploadedBlob = storage.create(blobInfo, file.getBytes());
        return "https://storage.googleapis.com/" + googleBucketName + "/" + uploadedBlob.getName();
    }

    public String getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = userRepository.findById(authentication.getName()).orElse(null);
            return user;
        }
        return null;
    }

    public UserResponse userToResponse(com.example.recruitment2.Entity.User user){
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .VNeId(user.getVNeId())
                .DOB(user.getDOB())
                .name(user.getName())
                .gender(user.getGender())
                .status(user.getStatus())
                .avatar(user.getAvatar())
                .build();
        if(user.getAreaId() != null){
            response.setArea(areaRepository.findById(user.getAreaId()).orElse(null));
        }
        if(user.getMemberOf() != null){
            List<OrganizationInfo> memberOf = new ArrayList<>();
            for (String id : user.getMemberOf()) {
                Organization organization = organizationRepository.findById(id).orElse(null);
                if(organization != null){
                    memberOf.add(OrganizationInfo.of(organization));
                }
            }
            response.setMemberOf(memberOf);
        }
        if(user.getFollowerOf() != null){
            List<OrganizationInfo> followerOf = new ArrayList<>();
            for (String id : user.getFollowerOf()) {
                Organization organization = organizationRepository.findById(id).orElse(null);
                if(organization != null){
                    followerOf.add(OrganizationInfo.of(organization));
                }
            }
            response.setFollowerOf(followerOf);
        }
        if(user.getInterestedIn() != null){
            List<EventInfo> interestedIn = new ArrayList<>();
            for (String id : user.getInterestedIn()) {
                Event event = eventRepository.findById(id).orElse(null);
                if(event != null){
                    interestedIn.add(EventInfo.of(event));
                }
            }
            response.setInterestedIn(interestedIn);
        }
        return response;
    }

    public AddressResponse addressToResponse(Address address){
        Area area = areaRepository.findById(address.getAreaId()).orElse(null);
        if(area == null){
            return null;
        }
        AddressResponse response = AddressResponse.builder()
                .areaId(address.getAreaId())
                .detail(address.getDetail())
                .province(area.getProvince())
                .district(area.getDistrict())
                .build();
        return response;
    }
}
