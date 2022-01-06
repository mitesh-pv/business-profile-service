package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileRequest;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;


public interface IBusinessProfileService {

    BusinessProfileEntity createBusinessProfile(final BusinessProfileEntity businessProfileEntity);
    BusinessProfileEntity updateBusinessProfile(final String taxId, final BusinessProfileRequest businessProfileRequest) throws IllegalAccessException;
    void sendBusinessProfileUpdateRequest(final String taxId, final BusinessProfileRequest businessProfileRequest) throws Exception;
    BusinessProfileEntity findByTaxId(final String taxId);
    BusinessProfileEntity findByEmail(final String email);


}
