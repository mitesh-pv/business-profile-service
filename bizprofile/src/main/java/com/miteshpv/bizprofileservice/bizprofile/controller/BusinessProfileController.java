package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.miteshpv.bizprofileservice.bizprofile.service.IBusinessProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.miteshpv.bizprofileservice.bizprofile.utils.Constants.BUSINESS_PROFILE_ROOT;

@RestController
@RequestMapping(BUSINESS_PROFILE_ROOT)
public class BusinessProfileController {

    @Autowired
    private IBusinessProfileService businessProfileService;

    @PostMapping("/v1/create-profile")
    public String createBusinessProfile(@RequestBody String profile) {
        return "SUCCESS";
    }

}
