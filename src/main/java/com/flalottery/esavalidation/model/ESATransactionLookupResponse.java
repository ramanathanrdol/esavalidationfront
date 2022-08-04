package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ESATransactionLookupResponse {
    private String code;
    private String id;
    private String status;
    private String rejectReason;
    private Wagers[] wagers;
}
