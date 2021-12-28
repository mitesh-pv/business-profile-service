package com.miteshpv.bizprofileservice.bizprofile.service;

public interface IAWSSNSService <T> {
    void sendNotification(T message);
}
