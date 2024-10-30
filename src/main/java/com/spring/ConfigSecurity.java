package com.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.backend.security.bean.SysPreConfig;
import com.backend.security.filter.JwtAuthFilter;
import com.backend.security.filter.SysAssistenceEncoderFilter;

import lombok.extern.slf4j.Slf4j;

//@formatter:off

@Configuration
@Slf4j
@ComponentScan(basePackages = { "com.error"})
@Import(value = { ConfigFactory.class })
@EnableWebSecurity
public class ConfigSecurity {


    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    
    @Autowired
    private SysAssistenceEncoderFilter assistenceEncoderFilter;
    
    @Autowired
    private SysPreConfig config;

    // WHITELIST de endereços que não serão autenticados
    private static final String[] AUTH_WHITELIST = { 
	    "/index.html",
	    "/favicon.ico",
	    "/error" // Quando for para produção desativar esse erro
    };
    
    // WHITELIST de endereços que não serão autenticados
    private static final String[] AUTH_WHITELIST_SWAGGER = { 
	    "/swagger-ui.html", 
	    "/swagger-ui/**", 
	    "/csrf/**", 
	    "/configuration/**",
	    "/swagger-resources/**", 
	    "/v3/api-docs/**", 
	    "/webjars/**" 
    };
    
    
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
	
	config.init();
	http.authorizeHttpRequests(requests -> requests
                	.requestMatchers(AUTH_WHITELIST).permitAll()
                	.requestMatchers(AUTH_WHITELIST_SWAGGER).permitAll()
                	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                	.anyRequest().authenticated()
		)
	.addFilterBefore(assistenceEncoderFilter, UsernamePasswordAuthenticationFilter.class)
	.addFilterAt(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	http.csrf(AbstractHttpConfigurer::disable);
	
	return http.build();
    }

    //@formatter:on

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
	var configuration = corsConfiguration();
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", configuration);
	return source;
    }

    private CorsConfiguration corsConfiguration() {
	log.info(AUTH_WHITELIST.toString());
	var corsConfiguration = new CorsConfiguration();
	corsConfiguration.setAllowedHeaders(JwtAuthFilter.HEADERS());
	corsConfiguration.setAllowedOrigins(JwtAuthFilter.ORIGINS());
	corsConfiguration.setAllowedMethods(JwtAuthFilter.METHODS());
	corsConfiguration.setAllowCredentials(true);
	corsConfiguration.setExposedHeaders(List.of("content-type"));
	return corsConfiguration;
    }

}
