package com.miteshpv.bizprofileservice.bizprofile.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessProfileResponse extends ApiResponse{
    private String email;
    private String legalName;
    private String companyName;
    private String website;
    private String legalAddress;
    private BusinessAddress businessAddress;
}
