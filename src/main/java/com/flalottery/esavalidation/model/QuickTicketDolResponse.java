package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Lombok;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuickTicketDolResponse extends DolResponse {
    private String rejectReason;
    private String drawStartDate;
    private String drawEndDate;
    private String gameName;
}
