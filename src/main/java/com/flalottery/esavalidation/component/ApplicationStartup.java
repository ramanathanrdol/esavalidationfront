package com.flalottery.esavalidation.component;

import com.flalottery.esavalidation.services.ESAPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class ApplicationStartup{

    @Autowired
    ESAPropertyService esaPropertyService;
        @PostConstruct
        public void init(){
            // start your monitoring in here
            //esaPropertyService = new ESAPropertyService();
            final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
            esaPropertyService.getEsaPropertiesDetail();
        }
}