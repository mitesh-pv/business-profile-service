package com.miteshpv.bizprofileservice.bizprofile.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSConfigProperties;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
//@EnableDynamoDBRepositories
//        (basePackages = "com.miteshpv.bizprofileservice.bizprofile.repository")
public class DynamoDBConfiguration {

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Autowired
    AWSConfigProperties awsConfigProperties;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAWSDynamoDB());
    }

    private AmazonDynamoDB buildAWSDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        awsConfigProperties.getDynamodb().getServiceEndpoint(),
                        awsConfigProperties.getDynamodb().getRegion()))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }

    /*@Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder builder = configDBClientBuilder();
        return builder.build();
    }

    private AmazonDynamoDBClientBuilder configDBClientBuilder() {

        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        builder.setEndpointConfiguration(amazonEndpointConfiguration());
        builder.withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)));
        return builder;
    }

    private AwsClientBuilder.EndpointConfiguration amazonEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(awsConfigProperties.getDynamodb().getServiceEndpoint(),
                awsConfigProperties.getDynamodb().getRegion());
    }*/



}
