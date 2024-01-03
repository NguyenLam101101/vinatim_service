package com.example.recruitment2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum EStatusCode {
    VNEID_EXISTED(101, "VNeId existed"),
    EMAIL_EXISTED(102, "Email existed"),
    PHONE_EXISTED(103, "Phone existed"),
    NOT_EXIST_USER(104, "User does not exist"),
    INVALID_PASSWORD(105, "Invalid password"),
    UNAUTHENTICATED(401, "Cannot determine user"),
    FORBIDDEN(403, "Do not have permission"),
    NOT_FOUND_OBJECT(106, "Not found object");
    public int code;
    public String detail;
}
