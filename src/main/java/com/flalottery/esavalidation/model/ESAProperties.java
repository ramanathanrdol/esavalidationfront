package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ESAProperties {
    private String promotion;
    private PromotionPropertiesDetail promotionPropertiesDetail;
}
