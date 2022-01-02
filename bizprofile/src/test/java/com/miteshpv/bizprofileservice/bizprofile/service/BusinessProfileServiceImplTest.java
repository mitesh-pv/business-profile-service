package com.miteshpv.bizprofileservice.bizprofile.service;

import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileRequest;
import com.miteshpv.bizprofileservice.bizprofile.entity.PersonEntitlementResponse;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;
import com.miteshpv.bizprofileservice.bizprofile.repository.AWSDynamoDBDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileServiceImplTest {

    @InjectMocks
    private BusinessProfileServiceImpl businessProfileService;

    @Mock
    private AWSDynamoDBDao awsDynamoDBDao;

    @Mock
    private AWSSNSSQSServiceImpl notificationService;

    @Mock
    private IProductEntitlementService productEntitlementService;

    @BeforeEach
    public void init() {
    }
    
    @Test
    public void testCreateBusinessProfile() {
        BusinessProfileEntity entity = new BusinessProfileEntity("taxId", "email", "legalName", "companyName", "website", "legalAddress", null, 1L);
        Mockito.when(awsDynamoDBDao.save(Mockito.any())).thenReturn(entity);
        Assertions.assertNotNull(businessProfileService.createBusinessProfile(entity));
    }

    @Test
    public void testUpdateBusinessProfile() throws IllegalAccessException {
        BusinessProfileRequest req = new BusinessProfileRequest();
        BusinessProfileEntity entity = new BusinessProfileEntity();
        entity.setEmail("demo@test.com");
        Mockito.when(awsDynamoDBDao.findById(Mockito.anyString(), Mockito.eq(BusinessProfileEntity.class)))
                .thenReturn(entity);
        Assertions.assertNotNull(businessProfileService.updateBusinessProfile("txId", req));
    }

    @Test
    public void testSendBusinessProfileUpdateRequest() throws Exception {
        BusinessProfileRequest req = new BusinessProfileRequest();
        BusinessProfileEntity entity = new BusinessProfileEntity();
        PersonEntitlementResponse res = new PersonEntitlementResponse();
        res.setEntitledProdList(Arrays.asList("QB-ACC", "QB-PRL"));
        Mockito.when(awsDynamoDBDao.findById(Mockito.anyString(), Mockito.eq(BusinessProfileEntity.class)))
                .thenReturn(entity);
        Mockito.when(productEntitlementService.getEntitledProduct(Mockito.anyString()))
                .thenReturn(res);
        Assertions.assertDoesNotThrow(() -> businessProfileService.sendBusinessProfileUpdateRequest("tax", req));
    }

    @Test
    public void testSendBusinessProfileUpdateRequestEntitlementNotFoundExcepction() throws Exception {
        Mockito.when(awsDynamoDBDao.findById(Mockito.anyString(), Mockito.eq(BusinessProfileEntity.class)))
                .thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> businessProfileService.sendBusinessProfileUpdateRequest("tax", new BusinessProfileRequest()));
    }

}
