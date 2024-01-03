package com.example.recruitment2.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private String province;
    private String district;
    private String areaId;
    private String detail;
}
