package com.spring;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.security.filter.JwtAuthFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ComponentScan(basePackages = { 
		"com.persistence.commun.component", 
		"com.backend.security.repository.impl",
		"com.persistence.commun.service.component", 
		"com.backend.security.service.impl", 
		"com.backend.security.bean",
		"com.backend.security.filter", 
		})
@Slf4j
public class ConfigFactory {
    
    @Bean("passwordEncoder")
    @Order(4999)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	return config.getAuthenticationManager();
    }

    @Bean("filterRegistrationBeanJwtAuthFilter")
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter(JwtAuthFilter jwtAuthFilter) {
	var registrationBean = new FilterRegistrationBean<JwtAuthFilter>();
	registrationBean.setFilter(jwtAuthFilter);
	registrationBean.addUrlPatterns("/**");
	log.info("Path filter jwt:  /*");
	registrationBean.setOrder(1023);
	return registrationBean;
    }
}
