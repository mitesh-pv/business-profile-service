package com.miteshpv.bizprofileservice.bizprofile.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSConfigProperties;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSSNSConfigProperties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AWSSNSSQSServiceImplTest {

    @InjectMocks
    private AWSSNSSQSServiceImpl awsSnsSqsService;

    @Mock
    private AWSConfigProperties awsConfigProperties;

    @Mock
    private AmazonSNSClient amazonSNSClient;

    @Test
    public void testSendNotification() {
        AWSSNSConfigProperties configProperties = new AWSSNSConfigProperties("region", "req-topic", "res-topic");
        Mockito.when(awsConfigProperties.getSns()).thenReturn(configProperties);
        Assertions.assertDoesNotThrow(() -> awsSnsSqsService.sendNotification("test message"));
    }

    @Test
    public void testSendNotificationExceptionCase() {
        AWSSNSConfigProperties configProperties = new AWSSNSConfigProperties("region", "req-topic", "res-topic");
        Mockito.when(awsConfigProperties.getSns()).thenReturn(configProperties);
        Mockito.when(amazonSNSClient.publish(Mockito.any()))
                .thenThrow(AmazonSNSException.class);
        Assertions.assertThrows(AmazonSNSException.class, () -> awsSnsSqsService.sendNotification("test message"));
    }
}
