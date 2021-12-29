package com.miteshpv.bizprofileservice.bizprofile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileRequest;
import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileUpdateNotificationEntity;
import com.miteshpv.bizprofileservice.bizprofile.entity.PersonEntitlementResponse;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;
import com.miteshpv.bizprofileservice.bizprofile.repository.AWSDynamoDBDao;
import com.miteshpv.bizprofileservice.bizprofile.utils.Constants;
import com.miteshpv.bizprofileservice.bizprofile.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BusinessProfileServiceImpl implements IBusinessProfileService{

    @Autowired
    private AWSDynamoDBDao businessProfileDao;

    @Autowired
    private AWSSNSSQSServiceImpl notificationService;

    @Autowired
    IProductEntitlementService productEntitlementService;

    @Override
    public BusinessProfileEntity createBusinessProfile(final BusinessProfileEntity businessProfileEntity) {
        return (BusinessProfileEntity)businessProfileDao.save(businessProfileEntity);
    }

    @Override
    public BusinessProfileEntity updateBusinessProfile(final String taxId, final BusinessProfileRequest businessProfileRequest) throws IllegalAccessException {
        BusinessProfileEntity entity = (BusinessProfileEntity) businessProfileDao.findById(taxId, BusinessProfileEntity.class);
        try {
            Utility.copyObject(entity, businessProfileRequest);
            businessProfileDao.saveOrUpdate(entity);

        }catch (IllegalAccessException | NoSuchFieldException exp) {
            log.error("Error copying source bean values to destination");
            throw new IllegalAccessException("Bean Copy Exception");
        }
        return entity;
    }

    @Override
    public void sendBusinessProfileUpdateRequest(final String taxId, final BusinessProfileRequest businessProfileRequest) throws Exception {
        BusinessProfileEntity entity = (BusinessProfileEntity) businessProfileDao.findById(taxId, BusinessProfileEntity.class);
        if(null == entity) {
            log.error("Profile with taxId : {} not found", taxId);
            throw new Exception(String.format("Profile with taxId : {} not found", taxId));
        }

        try {
            Utility.copyObject(entity, businessProfileRequest);
        }catch (IllegalAccessException | NoSuchFieldException exp) {
            log.error("Error copying source bean values to destination");
            throw new IllegalAccessException("Bean Copy Exception");
        }

        PersonEntitlementResponse entitlements = (PersonEntitlementResponse)productEntitlementService.getEntitledProduct(taxId);
        final BusinessProfileUpdateNotificationEntity notificationEntity = new BusinessProfileUpdateNotificationEntity();
        final Map<String, String> prodResMap = new HashMap<>();
        entitlements.getEntitledProdList().stream()
                .forEach(p -> prodResMap.put(p, Constants.NA));
        notificationEntity.setProductRes(prodResMap);
        notificationEntity.setBusinessProfileEntity(entity);
        notificationEntity.setRequestId(Utility.getUUId());
        ObjectMapper mapper = new ObjectMapper();
        notificationService.sendNotification(mapper.writeValueAsString(notificationEntity));
    }

    @Override
    public BusinessProfileEntity findByEmail(final String email) {
        List<BusinessProfileEntity> list = (List<BusinessProfileEntity>) businessProfileDao.findAllByAttribute("email", Arrays.asList(email), BusinessProfileEntity.class);
        return list.get(0);
    }

}
