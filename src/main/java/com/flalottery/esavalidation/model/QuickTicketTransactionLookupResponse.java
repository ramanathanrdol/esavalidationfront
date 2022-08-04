package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuickTicketTransactionLookupResponse extends ESATransactionLookupResponse{
    private long cashAmount;
    private String serialNumber;
    private long transactionTime;
    private String gameName;
    private QuickTicketWagers fromWager;
}
