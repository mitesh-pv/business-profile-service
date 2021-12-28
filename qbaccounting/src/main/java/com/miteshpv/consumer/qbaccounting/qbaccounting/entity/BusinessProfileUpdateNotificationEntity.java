package com.miteshpv.consumer.qbaccounting.qbaccounting.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
