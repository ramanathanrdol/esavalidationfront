package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppProperties {
    private long propertyId;
    private String applicationName;
    private long promoId;
    private String promoName;
    private String propertyKey;
    private String propertyValue;
}
