package com.miteshpv.bizprofileservice.bizprofile.config.properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = AWSConfigProperties.class)
@ActiveProfiles("test")
public class AWSConfigPropertiesTest {

    @Autowired
    private AWSConfigProperties awsConfigProperties;

    @AfterAll
    public static void testAllArgsConstructor() {
        Assertions.assertNotNull(new AWSConfigProperties(null, null, null));
        Assertions.assertNotNull(new AWSDynamoDBConfigProperties(null, null));
        Assertions.assertNotNull(new AWSSNSConfigProperties(null, null, null));
        Assertions.assertNotNull(new AWSSQSConfigProperties(null, null, null));
    }

    @Test
    public void testDynamoDBAWSConfigProperties() {
        Assertions.assertEquals(awsConfigProperties.getDynamodb().getRegion(), "us-east-2");
        Assertions.assertEquals(awsConfigProperties.getDynamodb().getServiceEndpoint(), "serviceEndPoint");
    }

    @Test
    public void testSNSAWSConfigProperties() {
        Assertions.assertEquals(awsConfigProperties.getSns().getSnsRegion(), "us-east-2");
        Assertions.assertEquals(awsConfigProperties.getSns().getResSnsTopicArn(), "resSNSTopicARN");
        Assertions.assertEquals(awsConfigProperties.getSns().getReqSnsTopicArn(), "reqSNSTopicARN");
    }

    @Test
    public void testSQSAWSConfigProperties() {
        Assertions.assertEquals(awsConfigProperties.getSqs().getSqsRegion(), "us-east-2");
        Assertions.assertEquals(awsConfigProperties.getSqs().getSqsTopicArn(), "SQSTopicARN");
        Assertions.assertEquals(awsConfigProperties.getSqs().getUri(), "testURL");
    }

}
