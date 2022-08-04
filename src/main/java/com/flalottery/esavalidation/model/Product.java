package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class Product {
    private String productName;
    private String productCode;
    private String dayMaxCount;
    private String invalidProductErrorMsg;
}
