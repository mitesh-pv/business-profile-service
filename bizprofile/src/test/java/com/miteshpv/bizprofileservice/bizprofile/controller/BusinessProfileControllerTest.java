package com.miteshpv.bizprofileservice.bizprofile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miteshpv.bizprofileservice.bizprofile.entity.BusinessProfileRequest;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessAddress;
import com.miteshpv.bizprofileservice.bizprofile.model.BusinessProfileEntity;
import com.miteshpv.bizprofileservice.bizprofile.service.IBusinessProfileService;
import org.junit.jupiter.api.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {BusinessProfileController.class})
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BusinessProfileControllerTest {

    @MockBean
    private IBusinessProfileService businessProfileService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    public void init() {
    }

    @Test
    public void testCreateBusinessProfile() throws Exception {
        BusinessProfileEntity entity = BusinessProfileEntity.builder()
                .taxId("TAXID").businessAddress(new BusinessAddress("line", "line", "city", "state", "zip", "country"))
                .companyName("Company").legalName("Name").email("email").build();
        Mockito.when(businessProfileService.createBusinessProfile(Mockito.any()))
                .thenReturn(entity);

        MvcResult mvcResult = mockMvc.perform(post("/api/business-profile/v1/create-profile")
                    .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(entity)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        BusinessProfileEntity res = mapper.readValue(mvcResult.getResponse().getContentAsString(), BusinessProfileEntity.class);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(res.getCompanyName(), "Company");
    }

    @Test
    public void testUpdateBusinessProfile() throws Exception {
        BusinessProfileRequest req = new BusinessProfileRequest("taxId", "email", "legalName", "companyName", "website", "legalAddress", new BusinessAddress());
        mockMvc.perform(put("/api/business-profile/v1/update-profile/{taxId}", "taxId")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testFindBusinessProfileByEmail() throws Exception {
        BusinessProfileEntity entity = BusinessProfileEntity.builder()
                .taxId("TAXID").businessAddress(new BusinessAddress("line", "line", "city", "state", "zip", "country"))
                .companyName("Company").legalName("Name").email("email").build();
        Mockito.when(businessProfileService.findByEmail(Mockito.anyString()))
                .thenReturn(entity);
        mockMvc.perform(get("/api/business-profile/v1/get-profile-by-email/{email}", "taxId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
