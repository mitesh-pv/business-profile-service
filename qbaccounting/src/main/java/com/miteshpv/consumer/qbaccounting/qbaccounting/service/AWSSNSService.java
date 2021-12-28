package com.miteshpv.consumer.qbaccounting.qbaccounting.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.miteshpv.consumer.qbaccounting.qbaccounting.config.properties.AWSConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AWSSNSService <T> {

    @Autowired
    private AWSConfigProperties awsConfigProperties;

    @Autowired
    private AmazonSNSClient amazonSNSClient;

    public void sendNotification(T message) {
        try {
            final PublishRequest publishRequest =
                    new PublishRequest(awsConfigProperties.getSns().getReqSnsTopicArn(), message.toString(), "SNS Request for update approval");
            amazonSNSClient.publish(publishRequest);
        }catch (AmazonSNSException e) {
            log.error("Error sending notification to SNS topic, exp : {}", e.getStackTrace());
            throw new AmazonSNSException(e.getErrorMessage());
        }
    }
}
