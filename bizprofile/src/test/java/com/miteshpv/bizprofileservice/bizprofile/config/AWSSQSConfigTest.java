package com.miteshpv.bizprofileservice.bizprofile.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
public class AWSSQSConfigTest {

    @InjectMocks
    private AWSSQSConfig awsSqsConfig;

    @Mock
    private Environment environment;

    @BeforeEach
    public void init() {
        Mockito.when(environment.getProperty(Mockito.anyString()))
                .thenReturn("TEST_STR");
    }

    @Test
    public void testSimpleMessageListenerContainerFactory() {
        awsSqsConfig.simpleMessageListenerContainerFactory();
    }

}
