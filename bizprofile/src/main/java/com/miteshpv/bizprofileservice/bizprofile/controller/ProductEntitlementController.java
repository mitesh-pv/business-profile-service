package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;
import com.miteshpv.bizprofileservice.bizprofile.service.IProductEntitlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.miteshpv.bizprofileservice.bizprofile.utils.Constants.PRODUCT_ENTITLEMENT_ROOT;

@RestController
@RequestMapping(PRODUCT_ENTITLEMENT_ROOT)
public class ProductEntitlementController {

    @Autowired
    private IProductEntitlementService entitlementService;
    // /api/product-entitlement
    @PostMapping("/v1/create-entitlement")
    public ResponseEntity<?> createEntitlement(@RequestBody ProductEntitlementEntity entitlementEntity) {
        ProductEntitlementEntity productEntitlementEntity = entitlementService.saveEntitlement(entitlementEntity);
        return new ResponseEntity<>(productEntitlementEntity, HttpStatus.CREATED);
    }

    @GetMapping("/v1/get-entitled-products/{personTaxId}")
    public ResponseEntity<?> getEntitledProducts(@PathVariable String personTaxId) {
        return new ResponseEntity<>(entitlementService.getEntitledProduct(personTaxId), HttpStatus.OK);
    }
}
