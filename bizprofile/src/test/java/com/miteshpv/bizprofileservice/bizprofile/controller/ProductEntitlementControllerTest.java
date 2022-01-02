package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miteshpv.bizprofileservice.bizprofile.model.ProductEntitlementEntity;
import com.miteshpv.bizprofileservice.bizprofile.service.IProductEntitlementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProductEntitlementController.class})
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductEntitlementControllerTest {

    @MockBean
    private IProductEntitlementService entitlementService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateEntitlement() throws Exception {
        ProductEntitlementEntity entitlementEntity = new ProductEntitlementEntity("id", "prod", "taxId");
        Mockito.when(entitlementService.saveEntitlement(Mockito.any()))
                .thenReturn(entitlementEntity);
        MvcResult mvcResult = mockMvc.perform(post("/api/product-entitlement/v1/create-entitlement")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(entitlementEntity)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    public void testGetEntitledProducts() throws Exception {
        mockMvc.perform(get("/api/product-entitlement/v1/get-entitled-products/{personTaxId}", "taxId"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
