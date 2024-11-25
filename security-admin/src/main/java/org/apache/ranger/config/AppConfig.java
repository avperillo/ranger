package org.apache.ranger.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.SessionScope;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/***
 * main app configuration
 */
//@Configuration
//@ComponentScan(basePackages = "org.apache.ranger")
//@EnableTransactionManagement
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws IOException {
        logger.debug("configure bean properties");

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocations(
                new ClassPathResource("core-site.xml"),
                new ClassPathResource("ranger-admin-default-site.xml"),
                new ClassPathResource("ranger-admin-site.xml")
        );
        propertiesFactoryBean.afterPropertiesSet();
        configurer.setProperties(Objects.requireNonNull(propertiesFactoryBean.getObject()));

        return configurer;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory(DataSource dataSource) {
        logger.debug("configure bean: defaultEntityManagerFactory");

        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setPersistenceUnitName("defaultPU");
        emFactory.setDataSource(dataSource);

        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("${ranger.jpa.jdbc.dialect}");
        vendorAdapter.setShowSql(Boolean.parseBoolean("${ranger.jpa.showsql}"));
        vendorAdapter.setGenerateDdl(false);
        emFactory.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("eclipselink.weaving", "false");
        emFactory.setJpaPropertyMap(jpaProperties);

        emFactory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

        return emFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory) {
        logger.debug("configure bean: transactionManager");

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(defaultEntityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean(destroyMethod = "close")
    public DataSource defaultDataSource() {
        logger.debug("configure bean: defaultDataSource");

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("${ranger.jpa.jdbc.driver}");
        dataSource.setJdbcUrl("${ranger.jpa.jdbc.url}");
        dataSource.setUsername("${ranger.jpa.jdbc.user}");
        dataSource.setPassword("${ranger.jpa.jdbc.password}");
        dataSource.setMaximumPoolSize(Integer.parseInt("${ranger.jpa.jdbc.maxpoolsize}"));
        dataSource.setMinimumIdle(Integer.parseInt("${ranger.jpa.jdbc.minpoolsize}"));
        dataSource.setIdleTimeout(Long.parseLong("${ranger.jpa.jdbc.idletimeout}"));
        dataSource.setConnectionTestQuery("${ranger.jpa.jdbc.preferredtestquery}");
        dataSource.setMaxLifetime(Long.parseLong("${ranger.jpa.jdbc.maxlifetime}"));
        dataSource.setConnectionTimeout(Long.parseLong("${ranger.jpa.jdbc.connectiontimeout}"));
        return dataSource;
    }

    @Bean
    public static CustomScopeConfigurer customScopeConfigure() {
    logger.debug("configure bean: customScopeConfigure");

        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("session", new SessionScope());
        scopes.put("request", new RequestScope());
        configurer.setScopes(scopes);
        return configurer;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        logger.debug("configure bean: messageSource");

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("WEB-INF/classes/internationalization/messages");
        return messageSource;
    }

    @Bean
    public RestTemplate restTemplate() {
        logger.debug("configure bean: restTemplate");

        return new RestTemplate();
    }

}
