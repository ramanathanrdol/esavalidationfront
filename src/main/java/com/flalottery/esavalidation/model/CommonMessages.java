package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class CommonMessages {
    private String commonMessage;
    private String dayMaxMessage;
    private String invalidTicketMessage;
    private String invalidPinMessage;
}
