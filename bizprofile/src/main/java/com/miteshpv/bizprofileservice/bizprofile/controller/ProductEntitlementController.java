package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.miteshpv.bizprofileservice.bizprofile.entity.ApiResponse;
import com.miteshpv.bizprofileservice.bizprofile.entity.PersonEntitlementResponse;
import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;
import com.miteshpv.bizprofileservice.bizprofile.service.IProductEntitlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.miteshpv.bizprofileservice.bizprofile.utils.Constants.PRODUCT_ENTITLEMENT_ROOT;

@RestController
@RequestMapping(PRODUCT_ENTITLEMENT_ROOT)
public class ProductEntitlementController {

    @Autowired
    private IProductEntitlementService entitlementService;

    @PostMapping("/v1/create-entitlement")
    public ResponseEntity<?> createEntitlement(@RequestBody ProductEntitlementEntity entitlementEntity) {
        ProductEntitlementEntity productEntitlementEntity = entitlementService.saveEntitlement(entitlementEntity);
        return new ResponseEntity<>(productEntitlementEntity, HttpStatus.CREATED);
    }

    @GetMapping("/v1/get-entitled-products/{personTaxId}")
    public ResponseEntity<?> getEntitledProducts(@PathVariable String personTaxId) {
        final PersonEntitlementResponse.PersonEntitlementResponseBuilder response = PersonEntitlementResponse.builder();
        response.taxId(personTaxId);

        final List<ProductEntitlementEntity> productEntitlementEntityList = entitlementService.getEntitledProduct(personTaxId);
        final List<String> entitledProducts = productEntitlementEntityList.stream()
                .map(entity -> entity.getEntitledProdCode())
                .collect(Collectors.toList());
        response.entitledProdList(entitledProducts);
        return new ResponseEntity<>((ApiResponse)response.build(), HttpStatus.OK);
    }
}
