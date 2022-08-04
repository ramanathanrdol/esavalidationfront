package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class TicketFormats {
    private String drawWithDash;
    private String drawWithOutDash;
    private String scratchOffPDF417;
    private String scratchOffInterleaved;
    private String scratchOffInterleavedPin;
    private String quickTicketFormat;

}
