package com.miteshpv.consumer.qbaccounting.qbaccounting.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miteshpv.consumer.qbaccounting.qbaccounting.entity.BusinessProfileUpdateNotificationEntity;
import com.miteshpv.consumer.qbaccounting.qbaccounting.entity.SQSNotificationMessageWrapper;
import com.miteshpv.consumer.qbaccounting.qbaccounting.service.AWSSNSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqsListenerService {

    @Value("${product.code}")
    private String productCode;

    @Autowired
    private AWSSNSService awsSnsService;

    @SqsListener(value = "#{'${cloud.aws.sqs.uri}'}",
            deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receive(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SQSNotificationMessageWrapper messageBody = mapper.readValue(message, SQSNotificationMessageWrapper.class);
        BusinessProfileUpdateNotificationEntity entity = mapper.readValue(messageBody.getMessage(), BusinessProfileUpdateNotificationEntity.class);
        if(!entity.getProductRes().containsKey(productCode)) {
            log.error("Product {}, not subscribed for user {}", productCode, entity.getBusinessProfileEntity().getTaxId());
        }else {
            entity.getProductRes().put(productCode, "approve");
            log.info("Message processed : {}", mapper.writeValueAsString(entity));
        }
    }
}
