package com;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;

import com.twa.Application;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * Quando for configurar um contexto especifico deve vir anotado
 * com @ComponentScan, se não não faz a leiturado os beans para as fabricas do
 * Spring
 */
@Scope("singleton")
@Configuration
@ComponentScan
@Import(value = { Application.class})
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@Slf4j
public class SampleConfigJUnit {

    @PostConstruct
    public void init() {
	log.info("init ...");
    }
}