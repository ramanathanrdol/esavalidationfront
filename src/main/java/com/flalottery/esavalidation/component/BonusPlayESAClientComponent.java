package com.flalottery.esavalidation.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flalottery.esavalidation.exception.ValidationWinStatus;
import com.flalottery.esavalidation.model.CommonMessages;
import com.flalottery.esavalidation.model.DolResponse;
import com.flalottery.esavalidation.model.TicketDetail;
import com.flalottery.esavalidation.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;

@Component
public class BonusPlayESAClientComponent {
    final Logger logger = LoggerFactory.getLogger(BonusPlayESAClientComponent.class);
    @Autowired
    AppComponent appComponent;
    @Autowired
    EntryCountServiceImpl entryCountService;
    @Autowired
    DrawGameValidationService drawGameValidationService;
    @Autowired
    InstantInterleavedValidationService instantInterleavedValidationService;
    @Autowired
    InstantPDF417ValidationService instantPDF417ValidationService;

    @Autowired(required = false)
    CommonMessages commonMessages;

    DolResponse dolResponse = new DolResponse();
    StringBuilder responseMessage;

    public DolResponse validateTicket(TicketDetail ticketDetail) throws JsonProcessingException {
        commonMessages = ticketDetail.getPromotionPropertiesDetail().getCommonMessages();
        responseMessage = new StringBuilder();
        if ((ticketDetail != null) && (!ticketDetail.getTicketNumber().isEmpty())) {
            ticketDetail.setMaxCountEntries(Long.parseLong(Objects.requireNonNull(ticketDetail.getPromotionPropertiesDetail().getProduct().getDayMaxCount())));
            if (entryCountService.checkMaxEntry(ticketDetail)) {
                return this.identifyAndValidate(ticketDetail);
            } else {
                responseMessage.append(commonMessages.getDayMaxMessage());
                dolResponse.setResponseMessage(responseMessage);
                dolResponse.setResponseStatus(ValidationWinStatus.DAY_MAX.getDescription());
                return dolResponse;
            }
        }

        responseMessage.append(commonMessages.getCommonMessage());
        dolResponse.setResponseMessage(responseMessage);
        dolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
        return dolResponse;
    }

    public DolResponse identifyAndValidate(TicketDetail ticketDetail) throws JsonProcessingException {
        //String commonErrorMessage = "The ticket entered is invalid. The Scratch-Off ticket number entered must be 24 digits or a FANTASY 5 ticket number entered must be 19 digits. If you still experience difficulty when submitting your ticket number, please visit the FAQs section or e-mail asklott@flalottery.com.";
        if ((ticketDetail != null) && (!ticketDetail.getTicketNumber().isEmpty())) {
            responseMessage = new StringBuilder();
            String ticketNo = ticketDetail.getTicketNumber();
            String pin = ticketDetail.getPin();
            logger.info(format("Ticket No : {0}", ticketNo));
            Matcher drawTicketMatcher = Pattern.compile(Objects.requireNonNull(ticketDetail.getPromotionPropertiesDetail().getTicketFormats().getDrawWithDash())).matcher(ticketNo);
            Matcher drawTicketNoDashMatcher = Pattern.compile(Objects.requireNonNull(ticketDetail.getPromotionPropertiesDetail().getTicketFormats().getDrawWithOutDash())).matcher(ticketNo);
            Matcher scratchOffPDF417Matcher = Pattern.compile(Objects.requireNonNull(ticketDetail.getPromotionPropertiesDetail().getTicketFormats().getScratchOffPDF417())).matcher(ticketNo);
            Matcher scartchOffInterleavedMatcher = Pattern.compile(Objects.requireNonNull(ticketDetail.getPromotionPropertiesDetail().getTicketFormats().getScratchOffInterleaved())).matcher(ticketNo);
            Matcher interleavedPinMatcher = Pattern.compile(Objects.requireNonNull(ticketDetail.getPromotionPropertiesDetail().getTicketFormats().getScratchOffInterleavedPin())).matcher(pin);

            logger.info(format("Ticket No : {0}", ticketNo));

            if (drawTicketMatcher.matches()) {
                return drawGameValidationService.validateTicket(drawGameValidationService.buildJsonRequest(ticketDetail));
            } else if (drawTicketNoDashMatcher.matches()) {
                ticketDetail.setTicketNumber(appComponent.formatDrawTicketNo(ticketDetail.getTicketNumber()));
                return drawGameValidationService.validateTicket(drawGameValidationService.buildJsonRequest(ticketDetail));
            } else if (scartchOffInterleavedMatcher.matches()) {
                if (interleavedPinMatcher.matches()) {
                    return instantInterleavedValidationService.validateTicket(instantInterleavedValidationService.buildJsonRequest(ticketDetail));
                } else {
                    responseMessage.append(commonMessages.getInvalidPinMessage());
                    dolResponse.setResponseMessage(responseMessage);
                    dolResponse.setResponseStatus(ValidationWinStatus.INVALID.getDescription());
                    return dolResponse;
                }
            } else if (scratchOffPDF417Matcher.matches()) {
                return instantPDF417ValidationService.validateTicket(instantPDF417ValidationService.buildJsonRequest(ticketDetail));
            } else {
                responseMessage.append(commonMessages.getInvalidTicketMessage());
                //logger.info("Response Message =======" + responseMessage);
                dolResponse.setResponseMessage(responseMessage);
                dolResponse.setResponseStatus(ValidationWinStatus.INVALID.getDescription());
                return dolResponse;
            }
        }
        responseMessage = new StringBuilder();
        //responseMessage.append(env.getProperty("esa.validation.error.message.common"));
        responseMessage.append(commonMessages.getCommonMessage());
        dolResponse.setResponseMessage(responseMessage);
        dolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
        return dolResponse;
    }
}
