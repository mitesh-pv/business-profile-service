package com.miteshpv.consumer.qbpayroll.qbpayroll.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessProfileEntity {

    private String taxId;

    private String email;

    private String legalName;

    private String companyName;

    private String website;

    private String legalAddress;

    private BusinessAddress businessAddress;

    private Long version;
}
