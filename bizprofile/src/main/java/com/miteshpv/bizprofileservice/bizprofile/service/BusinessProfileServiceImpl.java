package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileRequest;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;
import com.miteshpv.bizprofileservice.bizprofile.repository.AWSDynamoDBDao;
import com.miteshpv.bizprofileservice.bizprofile.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class BusinessProfileServiceImpl implements IBusinessProfileService{

    @Autowired
    private AWSDynamoDBDao businessProfileDao;

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
    public BusinessProfileEntity findByEmail(final String email) {
        List<BusinessProfileEntity> list = (List<BusinessProfileEntity>) businessProfileDao.findAllByAttribute("email", Arrays.asList(email), BusinessProfileEntity.class);
        return list.get(0);
    }

}
