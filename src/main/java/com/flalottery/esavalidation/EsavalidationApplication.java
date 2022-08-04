package com.flalottery.esavalidation;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@PropertySource(value = "file:${ESAPROPLOC}/validation.properties", ignoreResourceNotFound = true)
@ComponentScan
@EnableAutoConfiguration
@EnableCaching
public class EsavalidationApplication extends SpringBootServletInitializer {

    private static Class<EsavalidationApplication> applicationClass = EsavalidationApplication.class;

    public EsavalidationApplication() {
        super();
        setRegisterErrorPageFilter(false);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EsavalidationApplication.class);
    }

}
