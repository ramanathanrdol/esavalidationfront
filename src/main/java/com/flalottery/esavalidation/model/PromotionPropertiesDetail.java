package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class PromotionPropertiesDetail {
    private String promotionName;
    private String esaBonusPlayURI;
    private String esaWebsiteURI;
    private String drawURL;
    private String scratchOffURL;
    private String quickTicketURL;
    private String drawAPIKeyName;
    private String drawAPIKeyValue;
    private String scratchOffAPIKeyName;
    private String scratchOffAPIKeyValue;
    private String scratchOffMethodName;
    private String scratchOffMethodManual;
    private String quickTicketAPIKeyName;
    private String quickTicketAPIKeyValue;
    private String quickTicketMethodName;
    private String quickTicketMethodManual;
    private String requestSource;
    @Autowired
    private TicketFormats ticketFormats;
    @Autowired
    private Product product;
    @Autowired
    private CommonMessages commonMessages;
}
