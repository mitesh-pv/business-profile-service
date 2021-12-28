package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.miteshpv.bizprofileservice.bizprofile.config.properties.AWSConfigProperties;
import com.miteshpv.bizprofileservice.bizprofile.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AWSSQSSNSSubscriptionController {

    @Autowired
    private AWSConfigProperties awsConfigProperties;

    @Autowired
    private AmazonSNSClient amazonSNSClient;

    /*@GetMapping("/subscribe/sqs")
    public String subscribeToSQS() {
        final SubscribeRequest subscribeRequest =
                new SubscribeRequest(awsConfigProperties.getSns().getSnsTopicArn(),
                        Constants.PROTOCOL_SQS, awsConfigProperties.getSqs().getSqsTopicArn());
        log.info("SNS Topic ARN : {}", awsConfigProperties.getSns().getSnsTopicArn());
        log.info("SQS Topic ARN : {}", awsConfigProperties.getSqs().getSqsTopicArn());
        amazonSNSClient.subscribe(subscribeRequest);
        return "SQS Subscription Successful!";
    }*/

    @GetMapping("/subscribe/sns-sqs")
    public String subscribeSQSToSNS() {
        final SubscribeRequest subscribeRequest =
                new SubscribeRequest(awsConfigProperties.getSns().getResSnsTopicArn(),
                        Constants.PROTOCOL_SQS, awsConfigProperties.getSqs().getSqsTopicArn());
        log.info("SNS Topic ARN : {}", awsConfigProperties.getSns().getResSnsTopicArn());
        log.info("SQS Topic ARN : {}", awsConfigProperties.getSqs().getSqsTopicArn());
        amazonSNSClient.subscribe(subscribeRequest);
        return "SQS Subscription Successful!";
    }

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable(value = "message") String message) {
        final PublishRequest publishRequest =
                new PublishRequest(awsConfigProperties.getSns().getReqSnsTopicArn(), message, "SQS Demo");
        amazonSNSClient.publish(publishRequest);
        log.info("message sent success");
        return "Message published successfully";
    }
}
