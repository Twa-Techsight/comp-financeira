package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration("webappTestJUnit")
@EnableWebMvc
@ComponentScan
@ContextConfiguration(classes = SampleConfigJUnit.class)
public class ContextWebApplicationJUnit {

    @Autowired
    private WebApplicationContext webApplicationContext;

    public WebApplicationContext getWebApplicationContext() {
	return webApplicationContext;
    }

}