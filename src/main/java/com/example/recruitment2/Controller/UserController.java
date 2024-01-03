package com.example.recruitment2.Controller;

import com.example.recruitment2.DTO.EStatusCode;
import com.example.recruitment2.DTO.Request.UserRequest;
import com.example.recruitment2.DTO.Response.BaseResponse;
import com.example.recruitment2.DTO.Response.UserResponse;
import com.example.recruitment2.Entity.User;
import com.example.recruitment2.Repository.UserRepository;
import com.example.recruitment2.Service.CommonService;
import com.example.recruitment2.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final CommonService commonService;
    private final UserService userService;

    @GetMapping("/my-info")
    public ResponseEntity<Object> getMyInfo(){
        BaseResponse<UserResponse> response = new BaseResponse<>(0,"", null);
        User user = commonService.getCurrentUser();
        if(user == null){
            response.setCode(EStatusCode.UNAUTHENTICATED.code);
            return ResponseEntity.ok(response);
        }
        response.setData(commonService.userToResponse(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(UserRequest request) throws IOException {
        return ResponseEntity.ok(userService.updateUser(request));
    }
}
