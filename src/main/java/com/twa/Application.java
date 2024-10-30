package com.twa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class}, scanBasePackages = {
	"com.spring" })
public class Application extends SpringBootServletInitializer implements ApplicationRunner {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @PostConstruct
    public void init() {
	this.createSpringApplicationBuilder();
	log.info("started server ...........");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	return builder.sources(Application.class);
    }
    public static void main(String[] args) {
	var app = new SpringApplication(Application.class);
	Environment env = app.run(args).getEnvironment();
	var serverPort = env.getProperty("server.port");
	String contextPath = env.getProperty("server.servlet.context-path");
	log.info("server.port: " + serverPort);
	log.info("server.servlet.context-path: " + contextPath);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
	log.info("contextPath: " + contextPath);
    }

}
