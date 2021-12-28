package com.miteshpv.bizprofileservice.bizprofile.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName = "biz-profile")
public class BusinessProfileEntity {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String taxId;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String legalName;

    @DynamoDBAttribute
    private String companyName;

    @DynamoDBAttribute
    private String website;

    @DynamoDBAttribute
    private String legalAddress;

    @DynamoDBAttribute
    private BusinessAddress businessAddress;

    @DynamoDBVersionAttribute
    private Long version;
}
