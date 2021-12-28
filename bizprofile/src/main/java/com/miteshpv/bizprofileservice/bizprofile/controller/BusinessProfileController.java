package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.miteshpv.bizprofileservice.bizprofile.entity.ApiResponse;
import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileRequest;
import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileResponse;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;
import com.miteshpv.bizprofileservice.bizprofile.service.IBusinessProfileService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.miteshpv.bizprofileservice.bizprofile.utils.Constants.BUSINESS_PROFILE_ROOT;

@Slf4j
@RestController
@RequestMapping(BUSINESS_PROFILE_ROOT)
public class BusinessProfileController {

    @Autowired
    private IBusinessProfileService businessProfileService;

    @PostMapping("/v1/create-profile")
    public ResponseEntity<?> createBusinessProfile(@RequestBody BusinessProfileEntity businessProfileEntity) {
        return new ResponseEntity<>(businessProfileService.createBusinessProfile(businessProfileEntity), HttpStatus.CREATED);
    }

    @PutMapping("/v1/update-profile/{taxId}")
    public ResponseEntity<?> updateBusinessProfile(@PathVariable final String taxId, @RequestBody final BusinessProfileRequest businessProfileRequest) {
        try {
            businessProfileService.sendBusinessProfileUpdateRequest(taxId, businessProfileRequest);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exp) {
            log.error("Exception updating object");
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/v1/get-profile-by-email/{email}")
    public ResponseEntity<?> findBusinessProfileByEmail(@PathVariable String email) {
        final BusinessProfileEntity businessProfileEntity = businessProfileService.findByEmail(email);
        return new ResponseEntity<>(convertBusinessProfileEntityToApiResponse(businessProfileEntity), HttpStatus.OK);
    }

    private ApiResponse convertBusinessProfileEntityToApiResponse(final BusinessProfileEntity businessProfileEntity) {
        final BusinessProfileResponse.BusinessProfileResponseBuilder response = BusinessProfileResponse.builder();
        response.legalName(businessProfileEntity.getLegalName());
        response.taxId(businessProfileEntity.getTaxId());
        response.email(businessProfileEntity.getEmail());
        response.companyName(businessProfileEntity.getCompanyName());
        response.legalAddress(businessProfileEntity.getLegalAddress());
        response.businessAddress(businessProfileEntity.getBusinessAddress());
        return response.build();
    }

}
