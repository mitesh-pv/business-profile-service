package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;
import com.miteshpv.bizprofileservice.bizprofile.repository.AWSDynamoDBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductEntitlementServiceImpl implements IProductEntitlementService {

    @Autowired
    private AWSDynamoDBDao entitlementDao;

    @Override
    public ProductEntitlementEntity saveEntitlement(ProductEntitlementEntity productEntitlementEntity) {
        return (ProductEntitlementEntity)entitlementDao.save(productEntitlementEntity);
    }

    @Override
    public List<ProductEntitlementEntity> getEntitledProduct(String personTaxId) {
        return entitlementDao.findAllByAttribute("personTaxId", Arrays.asList(personTaxId), ProductEntitlementEntity.class);
    }
}
