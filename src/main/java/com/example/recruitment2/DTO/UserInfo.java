package com.example.recruitment2.DTO;

import com.example.recruitment2.Entity.User;
import lombok.Data;

@Data
public class UserInfo {
    private String id;
    private String name;
    private String avatar;

    public static UserInfo of(User user){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setAvatar(user.getAvatar());
        return userInfo;
    }
}
