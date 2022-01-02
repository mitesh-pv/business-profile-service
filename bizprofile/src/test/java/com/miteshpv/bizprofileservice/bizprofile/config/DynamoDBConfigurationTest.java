package com.miteshpv.bizprofileservice.bizprofile.config;

import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSConfigProperties;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSDynamoDBConfigProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
public class DynamoDBConfigurationTest {

    @InjectMocks
    private DynamoDBConfiguration dynamoDBConfiguration;

    @Mock
    private Environment environment;

    @Mock
    private AWSConfigProperties awsConfigProperties;

    @BeforeEach
    public void init() {
        Mockito.when(environment.getProperty(Mockito.anyString()))
                .thenReturn("TEST_STRING");
        final AWSDynamoDBConfigProperties dynamoDBConfigProperties = new AWSDynamoDBConfigProperties("REGION", "END-POINT");
        Mockito.when(awsConfigProperties.getDynamodb()).thenReturn(dynamoDBConfigProperties);
    }

    @Test
    public void testDynamoDBMapper() {
        Assertions.assertNotNull(dynamoDBConfiguration.dynamoDBMapper());
    }
}
