package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.entity.ApiResponse;
import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;

public interface IProductEntitlementService {

    ProductEntitlementEntity saveEntitlement(ProductEntitlementEntity productEntitlementEntity);
    ApiResponse getEntitledProduct(String personTaxId);
}
