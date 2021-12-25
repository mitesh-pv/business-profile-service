package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.repository.BusinessProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessProfileServiceImpl implements IBusinessProfileService{

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Override
    public void createBusinessProfile(String profile) {

    }
}
