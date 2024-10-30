package com.spring;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.support.TransactionTemplate;

import com.persistence.SysConfig;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@ComponentScan(basePackages = { 
	"com.persistence.commun.component",
	"com.persistence.commun.service.component",
	"com.twa.financeira.entity", 
	"com.twa.financeira.repository.impl",
	"com.twa.financeira.service.impl", 
	"com.webservices.server" 
})

//@Import(value = { ConfigSecurity.class })
public class Config {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    // Para saber qual ambiente esta operando, vem parametrizado pelo arquivo,
    // propertiesURLSystem.properties propriedade project_stage
    public static String AMBIENTE;

    @Value("${spring.liquibase.contexts}")
    private String contextLiquibase;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean("threadPoolTaskExecutor")
    @Order(1000)
    public Executor asyncExecutor() {
	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	executor.setCorePoolSize(10);
	executor.setMaxPoolSize(10000);
	executor.setQueueCapacity(500);
	executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
	executor.setThreadNamePrefix("Async-");
	log.info("initialize ... threadPoolTaskExecutor ");
	executor.afterPropertiesSet();
	return executor;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate createJdbcTemplate(@Qualifier(value = "dataSource") final DataSource dataSource) {
	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	jdbcTemplate.setDataSource(dataSource);
	return jdbcTemplate;
    }

    @Bean(name = "sessionFactoryParameter")
    @Order(5001)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Properties sessionFactoryProperties() {
	PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
	propertiesFactoryBean.setLocation(new ClassPathResource("/META-INF/hibernate-configuration.properties"));
	log.info("Ready pararameters: /META-INF/hibernate-configuration.properties");
	Properties properties = null;
	try {
	    propertiesFactoryBean.afterPropertiesSet();
	    properties = propertiesFactoryBean.getObject();

	} catch (IOException e) {
	    log.error("Cannot load properties file hibernate-configuration.properties");
	    throw new RuntimeException(e);
	}
	return properties;
    }

    /**
     * DataSource to access the system database
     *
     * @return: DataSource
     */
    @Bean(name = "dataSource")
    @Autowired
    @Order(5003)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSource restDataSource() {
	final BasicDataSource dataSource = new BasicDataSource();
	dataSource.setDriverClassName(driverClass);
	dataSource.setUsername(username);
	dataSource.setPassword(password);
	dataSource.setUrl(url);
	log.info("configured datasource bean: " + dataSource);
	return dataSource;

    }

    @Bean("springLiquibase")
    @Order(4998)
    public SpringLiquibase liquibase(@Qualifier(value = "dataSource") final DataSource dataSource) {
	SpringLiquibase liquibase = new SpringLiquibase();
	liquibase.setChangeLog("classpath:changelogs/db.changelog-master.xml");
	liquibase.setDataSource(dataSource);
	liquibase.setContexts(contextLiquibase);
	return liquibase;
    }

    /**
     * SessionFactory of the Hibernate,to build the access factory to the bank by
     * the hibernate, a hibernateSessionFactory
     *
     * @param: DataSource.
     * @return: SessionFactory is singleton
     */
    @Bean(name = SysConfig.SESSION_FACTORY)
    @Autowired
    @Order(5004)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SessionFactory sessionFactory(@Qualifier(value = "sessionFactoryParameter") final Properties propertiesFile,
	    SpringLiquibase liquibase) throws IOException {
	final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	sessionFactoryBean.setDataSource(liquibase.getDataSource());
	sessionFactoryBean.setHibernateProperties(loadPropertiesForHibernate(propertiesFile));
	sessionFactoryBean.setPackagesToScan("com.twa.financeira.entity");

	sessionFactoryBean.afterPropertiesSet();
	log.info("Create Session Factory  Hibernate");
	return sessionFactoryBean.getObject();
    }

    /** define date access database */
    private Properties loadPropertiesForHibernate(final Properties propertiesFile) {

	// SQLFeatureNotSupportedException org.postgresql.jdbc.PgConnection.createClob
	propertiesFile.put("hibernate.jdbc.lob.non_contextual_creation", "true");
	return propertiesFile;
    }

    @Bean
    @Order(5005)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @Order(5006)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
	return new HibernateExceptionTranslator();
    }

    /**
     * To manage hibernate transactions
     *
     * @param SessionFactory
     * @return HibernateTransactionManager
     */
    @Bean(name = SysConfig.TRANSACTION_MANAGER)
    @Autowired
    @Order(5007)
    public HibernateTransactionManager transactionManager(
	    @Qualifier(value = SysConfig.SESSION_FACTORY) final SessionFactory sessionFactory) {
	final HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
	transactionManager.afterPropertiesSet();
	transactionManager.setNestedTransactionAllowed(true);
	log.info("finish HibernateTransactionManager");
	return transactionManager;
    }

    /**
     * To manage hibernate TransactionTemplate
     *
     * @param HibernateTransactionManager
     * @return TransactionTemplate
     */
    @Bean(name = SysConfig.TRANSACTION_TEMPLATE)
    @Autowired
    @Order(5008)
    public TransactionTemplate transactionTemplate(
	    @Qualifier(value = "transactionManager") final HibernateTransactionManager transactionManager) {
	final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
	transactionManager.afterPropertiesSet();
	log.info("finish TransactionTemplate");
	return transactionTemplate;
    }

}
