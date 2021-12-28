package com.miteshpv.bizprofileservice.bizprofile.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileUpdateNotificationEntity;
import com.miteshpv.bizprofileservice.bizprofile.entity.SQSNotificationMessageWrapper;
import com.miteshpv.bizprofileservice.bizprofile.repository.AWSDynamoDBDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SQSUpdateNotificationResponseProcessor {

    private Map<String, BusinessProfileUpdateNotificationEntity> responseMap;

    @Autowired
    private AWSDynamoDBDao awsDynamoDBDao;

    @Autowired
    public SQSUpdateNotificationResponseProcessor() {
        this.responseMap = new HashMap<>();
    }

    @SqsListener(value = "#{'${cloud.aws.sqs.uri}'}",
            deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveUpdateNotificationResponse(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SQSNotificationMessageWrapper messageBody = mapper.readValue(message, SQSNotificationMessageWrapper.class);
        BusinessProfileUpdateNotificationEntity entity = mapper.readValue(messageBody.getMessage(), BusinessProfileUpdateNotificationEntity.class);

        log.info("Message received : " + entity);

        String reqId = entity.getRequestId();
        if (!responseMap.containsKey(reqId)) {
            responseMap.put(reqId, entity);
        } else {
            Map<String, String> resMap = responseMap.get(reqId).getProductRes();
            int prodCount = resMap.size();
            int approvedCount = 0;
            int resRecvdCnt = 0;
            for (Map.Entry<String, String> m : entity.getProductRes().entrySet()) {
                if (responseMap.get(reqId).getProductRes().get(m.getKey()).equals("no-res")) {
                    resMap.put(m.getKey(), m.getValue());
                }
            }

            for (Map.Entry<String, String> m : responseMap.get(reqId).getProductRes().entrySet()) {
                if (!m.getValue().equals("no-res")) {
                    resRecvdCnt++;
                }

                if (m.getValue().equals("approve")) {
                    approvedCount++;
                }
            }

            if (resRecvdCnt == prodCount) {
                log.info("Response from all products received");
                responseMap.remove(reqId);
                if (approvedCount == prodCount) {
                    awsDynamoDBDao.saveOrUpdate(entity.getBusinessProfileEntity());
                    log.info("Update applied in DB");
                }else {
                    log.info("Request rejected");
                }
            }
        }
    }
}
