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
public class TicketDetail {
    private String promotionName;
    private String ticketNumber;
    private String pin;
    private String ticketPattern;
    private String pinPattern;
    private String jsonRequest;
    private String method;
    private String playerId;
    private String gameNumber;
    private long maxCountEntries;
    private String source;

    @Autowired
    private PromotionPropertiesDetail promotionPropertiesDetail;
}
