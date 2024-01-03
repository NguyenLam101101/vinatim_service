package com.example.recruitment2.Service;

import com.example.recruitment2.DTO.EStatusCode;
import com.example.recruitment2.DTO.EventInfo;
import com.example.recruitment2.DTO.OrganizationInfo;
import com.example.recruitment2.DTO.Request.UserRequest;
import com.example.recruitment2.DTO.Response.BaseResponse;
import com.example.recruitment2.DTO.Response.UserResponse;
import com.example.recruitment2.DTO.UserInfo;
import com.example.recruitment2.Entity.Enum.EUserGender;
import com.example.recruitment2.Entity.Enum.EUserStatus;
import com.example.recruitment2.Repository.AreaRepository;
import com.example.recruitment2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final MongoTemplate mongoTemplate;
    private final CommonService commonService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        com.example.recruitment2.Entity.User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("not found email");
        }
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new User(user.getId(), user.getPassword(), grantedAuthorities);
    }

    public BaseResponse<String> createUser(UserRequest request){
        BaseResponse<String> response = new BaseResponse<>();
        if(userRepository.findUserByVNeId(request.getVNeId()) != null) {
            response.setCode(EStatusCode.VNEID_EXISTED.code);
            return response;
        }
        if(userRepository.findUserByEmail(request.getEmail()) != null) {
            response.setCode(EStatusCode.EMAIL_EXISTED.code);
            return response;
        }
        if(userRepository.findUserByPhone(request.getPhone()) != null) {
            response.setCode(EStatusCode.PHONE_EXISTED.code);
            return response;
        }
        com.example.recruitment2.Entity.User user = com.example.recruitment2.Entity.User.builder()
                .name(request.getName())
                .VNeId(request.getVNeId())
                .email(request.getEmail())
                .phone(request.getPhone())
                .areaId(request.getAreaId())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .status(EUserStatus.ACTIVE)
                .build();
        userRepository.save(user);
        response.setCode(0);
        return response;
    }

    public BaseResponse<String> updateUser(UserRequest request) throws IOException {
        BaseResponse<String> response = new BaseResponse<>();

        com.example.recruitment2.Entity.User currentUser = commonService.getCurrentUser();
        if(currentUser == null || !currentUser.getId().equals(request.getId())){
            response.setCode(EStatusCode.UNAUTHENTICATED.code);
            return response;
        }
        if(!currentUser.getVNeId().equals(request.getVNeId()) &&
                userRepository.findUserByVNeId(request.getVNeId()) != null) {
            response.setCode(EStatusCode.VNEID_EXISTED.code);
            return response;
        }
        if(!currentUser.getEmail().equals(request.getEmail()) &&
                userRepository.findUserByEmail(request.getEmail()) != null) {
            response.setCode(EStatusCode.EMAIL_EXISTED.code);
            return response;
        }
        if(!currentUser.getPhone().equals(request.getPhone()) &&
                userRepository.findUserByPhone(request.getPhone()) != null) {
            response.setCode(EStatusCode.PHONE_EXISTED.code);
            return response;
        }
        currentUser.setName(request.getName());
        currentUser.setVNeId(request.getVNeId());
        currentUser.setEmail(request.getEmail());
        currentUser.setPhone(request.getPhone());
        currentUser.setAreaId(request.getAreaId());
//        currentUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()))
        currentUser.setStatus(request.getStatus());
        currentUser.setDOB(request.getDOB());
        currentUser.setGender(request.getGender());
        if(request.getAvatarFile() != null){
            String avatar = commonService.uploadFile(request.getAvatarFile(), "avatar");
            currentUser.setAvatar(avatar);
        }
        userRepository.save(currentUser);
        response.setCode(0);
        return response;
    }

    public BaseResponse<String> login(String username, String password){
        BaseResponse response = new BaseResponse<>();
        com.example.recruitment2.Entity.User user = userRepository.findUserByVNeIdOrEmailOrPhone(username, username, username);
        if(user == null){
            response.setCode(EStatusCode.NOT_EXIST_USER.code);
            response.setMessage(EStatusCode.NOT_EXIST_USER.detail);
            return response;
        }
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            response.setCode(EStatusCode.INVALID_PASSWORD.code);
            response.setMessage(EStatusCode.INVALID_PASSWORD.detail);
            return response;
        }
        response.setCode(0);
        response.setData(jwtProvider.generateToken(user.getId()));
        return response;
    }

    public BaseResponse<UserInfo> findUsersByEmailOrPhone(String text){
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("email").regex("^" + text + "$", "i"),
                Criteria.where("phone").regex("^" + text + "$", "i")
        ));
        com.example.recruitment2.Entity.User user = mongoTemplate.findOne(query, com.example.recruitment2.Entity.User.class);
        BaseResponse<UserInfo> response = new BaseResponse<>();
        response.setCode(0);
        if(user != null)
            response.setData(UserInfo.of(user));
        return response;
    }
}

