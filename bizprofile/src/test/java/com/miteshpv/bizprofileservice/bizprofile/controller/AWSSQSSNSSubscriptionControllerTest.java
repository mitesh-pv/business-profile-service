package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSConfigProperties;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSSNSConfigProperties;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSSQSConfigProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {AWSSQSSNSSubscriptionController.class})
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AWSSQSSNSSubscriptionControllerTest {

    @MockBean
    private AWSConfigProperties awsConfigProperties;

    @MockBean
    private AmazonSNSClient amazonSNSClient;

    @Autowired
    private MockMvc mockMvc;

    public AWSSQSSNSSubscriptionControllerTest() {
    }

    @BeforeEach
    public void init() {
        AWSSNSConfigProperties sns = new AWSSNSConfigProperties("region", "reqARN", "resARN");
        AWSSQSConfigProperties sqs = new AWSSQSConfigProperties("sqsRegion", "sqsArn", "url");
        Mockito.when(awsConfigProperties.getSns()).thenReturn(sns);
        Mockito.when(awsConfigProperties.getSqs()).thenReturn(sqs);
    }

    @Test
    public void testSubscribeSQSToSNS() throws Exception {
        MvcResult result = mockMvc.perform(get("/subscribe/sns-sqs"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        Assertions.assertEquals(responseString, "SQS Subscription Successful!");
    }

    @Test
    public void testSendMessage() throws Exception {
        MvcResult result = mockMvc.perform(get("/send/{message}", "test-message"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        Assertions.assertEquals(responseString, "Message published successfully");
    }
}

