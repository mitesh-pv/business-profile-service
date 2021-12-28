package com.miteshpv.bizprofileservice.bizprofile.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BusinessProfileRequest {
    private String taxId;
    private String email;
    private String legalName;
    private String companyName;
    private String website;
    private String legalAddress;
    private BusinessAddress businessAddress;
}
