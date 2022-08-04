package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScratchInquireResponse {
    private String code;
    private String winStatus;
    private String prizeValueAmount;
    private String[] prizeTypes;
    private String resultCode;
    private String rejectReason;
}
