package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;

import java.util.List;

public interface IProductEntitlementService {

    ProductEntitlementEntity saveEntitlement(ProductEntitlementEntity productEntitlementEntity);
    List<ProductEntitlementEntity> getEntitledProduct(String personTaxId);
}
