package com.example.recruitment2.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;
}
