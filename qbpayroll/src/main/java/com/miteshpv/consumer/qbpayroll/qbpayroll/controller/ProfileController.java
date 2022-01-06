package com.miteshpv.consumer.qbpayroll.qbpayroll.controller;

import com.miteshpv.consumer.qbpayroll.qbpayroll.entity.BusinessProfileRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ProfileController {

    @Autowired
    public RestTemplate restTemplate;

    @PostMapping("/api/update")
    public ResponseEntity<?> updateProfile(@RequestBody BusinessProfileRequest request) {

        String url = "http://localhost:8080/api/business-profile/v1/update-profile/{taxId}";
        String taxId = request.getTaxId();

        HttpEntity<BusinessProfileRequest> entity = new HttpEntity<>(request);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, entity, BusinessProfileRequest.class, taxId);
        }catch(HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }
}
