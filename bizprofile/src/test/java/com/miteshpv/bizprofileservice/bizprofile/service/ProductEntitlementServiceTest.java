package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;
import com.miteshpv.bizprofileservice.bizprofile.repository.AWSDynamoDBDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductEntitlementServiceTest {

    @InjectMocks
    private ProductEntitlementServiceImpl productEntitlementService;

    @Mock
    private AWSDynamoDBDao entitlementDao;

    @Test
    public void saveEntitlement() {
        ProductEntitlementEntity entity = new ProductEntitlementEntity();
        Mockito.when(entitlementDao.save(Mockito.any())).thenReturn(entity);
        Assertions.assertNotNull(productEntitlementService.saveEntitlement(entity));
    }

    @Test
    public void testGetEntitledProduct() {
        Assertions.assertNotNull(productEntitlementService.getEntitledProduct("taxId"));
    }

}
