package com.miteshpv.consumer.qbaccounting.qbaccounting.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class BusinessAddress {

    @DynamoDBAttribute
    private String line1;

    @DynamoDBAttribute
    private String line2;

    @DynamoDBAttribute
    private String city;

    @DynamoDBAttribute
    private String state;

    @DynamoDBAttribute
    private String zip;

    @DynamoDBAttribute
    private String country;
}
