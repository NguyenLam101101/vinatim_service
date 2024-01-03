package com.example.recruitment2.Entity.SubEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Review {
    private String userId;
    private int rate;
//    private String comment;
}

