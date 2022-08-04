package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DolResponse {
    private StringBuilder responseMessage;
    private String responseStatus;
    private String ticketPrice;
}
