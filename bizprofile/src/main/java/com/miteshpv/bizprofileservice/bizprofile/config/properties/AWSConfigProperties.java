package com.miteshpv.bizprofileservice.bizprofile.config.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("cloud.aws")
public class AWSConfigProperties {
    private AWSSNSConfigProperties sns;
    private AWSSQSConfigProperties sqs;
    private AWSDynamoDBConfigProperties dynamodb;
}
