package com.miteshpv.bizprofileservice.bizprofile.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AWSSQSConfigProperties {
    @NotNull
    private String sqsRegion;
    @NotNull
    private String sqsTopicArn;
    @NotNull
    private String uri;
}
