package com.miteshpv.bizprofileservice.bizprofile.entity;

import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessProfileUpdateNotificationEntity {

    private String requestId;
    private Map<String, String> productRes;
    private BusinessProfileEntity businessProfileEntity;
}
