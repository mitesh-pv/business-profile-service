package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.entity.ApiResponse;
import com.miteshpv.bizprofileservice.bizprofile.entity.PersonEntitlementResponse;
import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;
import com.miteshpv.bizprofileservice.bizprofile.repository.AWSDynamoDBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductEntitlementServiceImpl implements IProductEntitlementService {

    @Autowired
    private AWSDynamoDBDao entitlementDao;

    @Override
    public ProductEntitlementEntity saveEntitlement(ProductEntitlementEntity productEntitlementEntity) {
        return (ProductEntitlementEntity)entitlementDao.save(productEntitlementEntity);
    }

    @Override
    public ApiResponse getEntitledProduct(String personTaxId) {

        final PersonEntitlementResponse.PersonEntitlementResponseBuilder response = PersonEntitlementResponse.builder();
        response.taxId(personTaxId);
        final List<ProductEntitlementEntity> productEntitlementEntityList = entitlementDao
                .findAllByAttribute("personTaxId", Arrays.asList(personTaxId), ProductEntitlementEntity.class);
        final List<String> entitledProducts = productEntitlementEntityList.stream()
                .map(entity -> entity.getEntitledProdCode())
                .collect(Collectors.toList());
        response.entitledProdList(entitledProducts);
        return response.build();
    }
}
