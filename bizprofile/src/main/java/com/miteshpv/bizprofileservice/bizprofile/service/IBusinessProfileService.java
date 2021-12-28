package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileRequest;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;

import java.util.List;

public interface IBusinessProfileService {

    BusinessProfileEntity createBusinessProfile(final BusinessProfileEntity businessProfileEntity);
    BusinessProfileEntity updateBusinessProfile(final String taxId, final BusinessProfileRequest businessProfileRequest) throws IllegalAccessException;
    BusinessProfileEntity findByEmail(final String email);


}
