package com.miteshpv.consumer.qbpayroll.qbpayroll.listener;

import lombok.extern.slf4j.Slf4j;
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

    @SqsListener(value = "#{'${cloud.aws.sqs.uri}'}",
            deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receive(@Header("MessageId") String messageId, @Header("ApproximateFirstReceiveTimestamp") String approximateFirstReceiveTimestamp,
                         String message) {
        log.info("Message received : " + message + " " + messageId + " " + approximateFirstReceiveTimestamp);
    }
}
