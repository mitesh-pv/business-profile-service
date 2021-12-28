package com.miteshpv.consumer.qbaccounting.qbaccounting.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AWSSNSConfigProperties {
    @NotNull
    private String snsRegion;
    @NotNull
    private String reqSnsTopicArn;
    @NotNull
    private String resSnsTopicArn;
}
