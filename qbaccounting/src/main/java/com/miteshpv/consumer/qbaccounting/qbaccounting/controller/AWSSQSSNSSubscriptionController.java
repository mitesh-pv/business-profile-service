package com.miteshpv.consumer.qbaccounting.qbaccounting.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miteshpv.consumer.qbaccounting.qbaccounting.config.properties.AWSConfigProperties;
import com.miteshpv.consumer.qbaccounting.qbaccounting.entity.BusinessProfileUpdateNotificationEntity;
import com.miteshpv.consumer.qbaccounting.qbaccounting.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class AWSSQSSNSSubscriptionController {

    @Autowired
    private AWSConfigProperties awsConfigProperties;

    @Autowired
    private AmazonSNSClient amazonSNSClient;

    @GetMapping("/subscribe/sns-sqs")
    public String subscribeSQSToSNS() {
        final SubscribeRequest subscribeRequest =
                new SubscribeRequest(awsConfigProperties.getSns().getReqSnsTopicArn(),
                        Constants.PROTOCOL_SQS, awsConfigProperties.getSqs().getSqsTopicArn());
        log.info("SNS Topic ARN : {}", awsConfigProperties.getSns().getReqSnsTopicArn());
        log.info("SQS Topic ARN : {}", awsConfigProperties.getSqs().getSqsTopicArn());
        amazonSNSClient.subscribe(subscribeRequest);
        return "SQS Subscription Successful!";
    }

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable(value = "message") String message) {
        final PublishRequest publishRequest =
                new PublishRequest(awsConfigProperties.getSns().getResSnsTopicArn(), message, "SQS Demo");
        amazonSNSClient.publish(publishRequest);
        log.info("message sent success");
        return "Message published successfully";
    }

    @PostMapping("/validate")
    public String validate(@RequestBody BusinessProfileUpdateNotificationEntity req) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        final PublishRequest publishRequest =
                new PublishRequest(awsConfigProperties.getSns().getResSnsTopicArn(), mapper.writeValueAsString(req),
                        "Validate");
        amazonSNSClient.publish(publishRequest);
        log.info("message sent success");
        return "Message published successfully";
    }


}
